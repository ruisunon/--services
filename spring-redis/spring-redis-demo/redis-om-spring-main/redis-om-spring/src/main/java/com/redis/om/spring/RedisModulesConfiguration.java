package com.redis.om.spring;

import ai.djl.MalformedModelException;
import ai.djl.huggingface.tokenizers.HuggingFaceTokenizer;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.transform.CenterCrop;
import ai.djl.modality.cv.transform.Resize;
import ai.djl.modality.cv.transform.ToTensor;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.Pipeline;
import com.github.f4b6a3.ulid.Ulid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redis.om.spring.annotations.Bloom;
import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.client.RedisModulesClient;
import com.redis.om.spring.ops.RedisModulesOperations;
import com.redis.om.spring.ops.json.JSONOperations;
import com.redis.om.spring.ops.pds.BloomOperations;
import com.redis.om.spring.search.stream.EntityStream;
import com.redis.om.spring.search.stream.EntityStreamImpl;
import com.redis.om.spring.serialization.gson.*;
import com.redis.om.spring.vectorize.FeatureExtractor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.gson.GsonBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.mapping.RedisMappingContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.redis.om.spring.util.ObjectUtils.getBeanDefinitionsFor;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({RedisProperties.class, RedisOMSpringProperties.class})
@EnableAspectJAutoProxy
@ComponentScan("com.redis.om.spring.bloom")
@ComponentScan("com.redis.om.spring.autocomplete")
@ComponentScan("com.redis.om.spring.metamodel")
public class RedisModulesConfiguration {

  private static final Log logger = LogFactory.getLog(RedisModulesConfiguration.class);

  @Bean
  public GsonBuilder gsonBuilder(List<GsonBuilderCustomizer> customizers) {

    GsonBuilder builder = new GsonBuilder();
    // Enable the spring.gson.* configuration in the configuration file
    customizers.forEach(c -> c.customize(builder));

    builder.registerTypeAdapter(Point.class, PointTypeAdapter.getInstance());
    builder.registerTypeAdapter(Date.class, DateTypeAdapter.getInstance());
    builder.registerTypeAdapter(LocalDate.class, LocalDateTypeAdapter.getInstance());
    builder.registerTypeAdapter(LocalDateTime.class, LocalDateTimeTypeAdapter.getInstance());
    builder.registerTypeAdapter(Ulid.class, UlidTypeAdapter.getInstance());
    builder.registerTypeAdapter(Instant.class, InstantTypeAdapter.getInstance());
    builder.registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeTypeAdapter());

    return builder;
  }

  @Bean(name = "redisModulesClient")
  RedisModulesClient redisModulesClient(JedisConnectionFactory jedisConnectionFactory, GsonBuilder builder) {
    return new RedisModulesClient(jedisConnectionFactory, builder);
  }

  @Bean(name = "redisModulesOperations")
  @Primary
  @ConditionalOnMissingBean
  RedisModulesOperations<?> redisModulesOperations(RedisModulesClient rmc, StringRedisTemplate template, GsonBuilder gsonBuilder) {
    return new RedisModulesOperations<>(rmc, template, gsonBuilder);
  }

  @Bean(name = "redisJSONOperations")
  JSONOperations<?> redisJSONOperations(RedisModulesOperations<?> redisModulesOperations) {
    return redisModulesOperations.opsForJSON();
  }

  @Bean(name = "redisBloomOperations")
  BloomOperations<?> redisBloomOperations(RedisModulesOperations<?> redisModulesOperations) {
    return redisModulesOperations.opsForBloom();
  }

  @Bean(name = "redisTemplate")
  @Primary
  public RedisTemplate<?, ?> redisTemplate(JedisConnectionFactory connectionFactory) {
    RedisTemplate<?, ?> template = new RedisTemplate<>();
    template.setKeySerializer(new StringRedisSerializer());
    template.setDefaultSerializer(new StringRedisSerializer());
    template.setConnectionFactory(connectionFactory);

    return template;
  }

  @Bean(name = "rediSearchIndexer")
  public RediSearchIndexer redisearchIndexer(ApplicationContext ac) {
    return new RediSearchIndexer(ac);
  }

  @Bean(name = "djlImageFactory")
  public ImageFactory imageFactory() {
    return ImageFactory.getInstance();
  }

  @Bean(name = "djlImageEmbeddingModelCriteria")
  public Criteria<Image, byte[]> imageEmbeddingModelCriteria(RedisOMSpringProperties properties) {
    return Criteria.builder()
        .setTypes(Image.class, byte[].class) //
        .optEngine(properties.getDjl().getImageEmbeddingModelEngine())  //
        .optModelUrls(properties.getDjl().getImageEmbeddingModelModelUrls()) //
        .build();
  }

  @Bean(name = "djlImageEmbeddingModel")
  public ZooModel<Image, byte[]> imageModel(
      @Qualifier("djlImageEmbeddingModelCriteria") Criteria<Image, byte[]> imageEmbeddingModelCriteria)
      throws MalformedModelException, ModelNotFoundException, IOException {
    return ModelZoo.loadModel(imageEmbeddingModelCriteria);
  }

  @Bean(name = "djlDefaultImagePipeline")
  public Pipeline defaultImagePipeline(RedisOMSpringProperties properties) {
    Pipeline pipeline = new Pipeline();
    if (properties.getDjl().isDefaultImagePipelineCenterCrop()) {
      pipeline.add(new CenterCrop());
    }
    return pipeline //
        .add(new Resize( //
            properties.getDjl().getDefaultImagePipelineResizeWidth(), //
            properties.getDjl().getDefaultImagePipelineResizeHeight() //
        )) //
        .add(new ToTensor());
  }

  @Bean(name = "djlSentenceTokenizer")
  public HuggingFaceTokenizer sentenceTokenizer(RedisOMSpringProperties properties) {
    Map<String, String> options = Map.of( //
        "maxLength", properties.getDjl().getSentenceTokenizerMaxLength(), //
        "modelMaxLength", properties.getDjl().getSentenceTokenizerModelMaxLength() //
    );
    return HuggingFaceTokenizer.newInstance(properties.getDjl().getSentenceTokenizerModel(), options);
  }

  @Bean(name = "featureExtractor")
  public FeatureExtractor featureExtractor(
      @Qualifier("djlImageEmbeddingModel") ZooModel<Image, byte[]> model,
      @Qualifier("djlImageFactory") ImageFactory imageFactory,
      @Qualifier("djlDefaultImagePipeline") Pipeline defaultImagePipeline,
      @Qualifier("djlSentenceTokenizer") HuggingFaceTokenizer sentenceTokenizer,
      @Qualifier("redisTemplate") RedisTemplate<?, ?> redisTemplate,
      ApplicationContext ac) {
    return new FeatureExtractor(redisTemplate, ac, model, imageFactory, defaultImagePipeline, sentenceTokenizer);
  }

  @Bean(name = "redisJSONKeyValueAdapter")
  RedisJSONKeyValueAdapter getRedisJSONKeyValueAdapter( //
      RedisOperations<?, ?> redisOps, //
      RedisModulesOperations<?> redisModulesOperations, //
      RedisMappingContext mappingContext, //
      RediSearchIndexer indexer, //
      GsonBuilder gsonBuilder, //
      RedisOMSpringProperties properties //
  ) {
    return new RedisJSONKeyValueAdapter(redisOps, redisModulesOperations, mappingContext, indexer, gsonBuilder, properties);
  }

  @Bean(name = "redisJSONKeyValueTemplate")
  public CustomRedisKeyValueTemplate getRedisJSONKeyValueTemplate( //
      RedisOperations<?, ?> redisOps, //
      RedisModulesOperations<?> redisModulesOperations, //
      RedisMappingContext mappingContext, //
      RediSearchIndexer indexer, //
      GsonBuilder gsonBuilder, //
      RedisOMSpringProperties properties //
  ) {
    return new CustomRedisKeyValueTemplate(
        new RedisJSONKeyValueAdapter(redisOps, redisModulesOperations, mappingContext, indexer, gsonBuilder, properties),
        mappingContext);
  }

  @Bean(name = "redisCustomKeyValueTemplate")
  public CustomRedisKeyValueTemplate getKeyValueTemplate( //
      RedisOperations<?, ?> redisOps, //
      RedisModulesOperations<?> redisModulesOperations, //
      RedisMappingContext mappingContext, //
      RediSearchIndexer indexer, //
      FeatureExtractor featureExtractor
  ) {
    return new CustomRedisKeyValueTemplate(
        new RedisEnhancedKeyValueAdapter(redisOps, redisModulesOperations, mappingContext, indexer, featureExtractor), //
        mappingContext);
  }

  @Bean(name = "streamingQueryBuilder")
  EntityStream streamingQueryBuilder(RedisModulesOperations<?> redisModulesOperations, Gson gson) {
    return new EntityStreamImpl(redisModulesOperations, gson);
  }

  @EventListener(ContextRefreshedEvent.class)
  public void ensureIndexesAreCreated(ContextRefreshedEvent cre) {
    logger.info("Creating Indexes......");

    ApplicationContext ac = cre.getApplicationContext();

    RediSearchIndexer indexer = (RediSearchIndexer) ac.getBean("rediSearchIndexer");
    indexer.createIndicesFor(Document.class);
    indexer.createIndicesFor(RedisHash.class);
  }

  @EventListener(ContextRefreshedEvent.class)
  public void processBloom(ContextRefreshedEvent cre) {
    ApplicationContext ac = cre.getApplicationContext();
    @SuppressWarnings("unchecked")
    RedisModulesOperations<String> rmo = (RedisModulesOperations<String>) ac.getBean("redisModulesOperations");

    Set<BeanDefinition> beanDefs = getBeanDefinitionsFor(ac, Document.class, RedisHash.class);

    for (BeanDefinition beanDef : beanDefs) {
      try {
        Class<?> cl = Class.forName(beanDef.getBeanClassName());
        for (java.lang.reflect.Field field : com.redis.om.spring.util.ObjectUtils.getDeclaredFieldsTransitively(cl)) {
          // Text
          if (field.isAnnotationPresent(Bloom.class)) {
            Bloom bloom = field.getAnnotation(Bloom.class);
            BloomOperations<String> ops = rmo.opsForBloom();
            String filterName = !ObjectUtils.isEmpty(bloom.name()) ? bloom.name()
                : String.format("bf:%s:%s", cl.getSimpleName(), field.getName());
            ops.createFilter(filterName, bloom.capacity(), bloom.errorRate());
          }
        }
      } catch (Exception e) {
        logger.debug("Error during processing of @Bloom annotation: ", e);
      }
    }
  }
}