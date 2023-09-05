package ai.sxr.shoppingla.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableCaching
public class RedisCacheConfig {

    public final static String BLACKLIST_CACHE_NAME = "jwt_token_black_list";
    private static final String LOCK_NAME = "lock";
    @Value("${application.security.jwt.expiration:0}")
    private int tokenDuration;

    @Value("${spring.data.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.data.redis.port:6379}")
    private int redisPort;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
    }

    //    @Bean
    //    @Profile(Constants.REDIS_PROFILE)
    //    public RedisConnectionFactory redisConnectionFactory(){
    //        RedisConnectionFactory connectionFactory = null;
    //        try{
    //            var config =new RedisStandaloneConfiguration(redisHost, redisPort);
    //            config.setPassword(redisPassword);
    //
    //            connectionFactory = new LettuceConnectionFactory(config);
    //        }catch(Exception e){
    //            e.printStackTrace();
    //            System.out.println("Unable to connect to redis at %s:%s");
    //        }
    //        return connectionFactory;
    //    }
    @Bean
    RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> {
            Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
            configurationMap.put(BLACKLIST_CACHE_NAME, RedisCacheConfiguration.defaultCacheConfig().entryTtl(
                    Duration.ofSeconds(tokenDuration)));
            builder.withInitialCacheConfigurations(configurationMap);
        };
    }

    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheConfiguration cacheConfig = myDefaultCacheConfig(Duration.ofMinutes(10)).disableCachingNullValues();

        return RedisCacheManager.builder(redisConnectionFactory())
                .cacheDefaults(cacheConfig)
                .withCacheConfiguration("ecommerce", myDefaultCacheConfig(Duration.ofMinutes(5)))
                .withCacheConfiguration("ecommerce", myDefaultCacheConfig(Duration.ofMinutes(1)))
                .build();
    }


    @Bean
    //@Profile(Constants.REDIS_PROFILE)
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(stringSerializer);
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();

        return template;
    }


    @Bean(destroyMethod = "destroy")
   // @Profile(Constants.REDIS_PROFILE)
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, LOCK_NAME);
    }
    private RedisCacheConfiguration myDefaultCacheConfig(Duration duration) {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(duration)
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }
}
