package com.redis.om.spring.annotations.hash.fixtures;

import com.redis.om.spring.DistanceMetric;
import com.redis.om.spring.VectorType;
import com.redis.om.spring.annotations.EmbeddingType;
import com.redis.om.spring.annotations.Indexed;
import com.redis.om.spring.annotations.SchemaFieldType;
import com.redis.om.spring.annotations.Vectorize;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import redis.clients.jedis.search.Schema.VectorField.VectorAlgo;

@Data
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor
@RedisHash
public class Product {
  @Id
  private String id;

  @Indexed
  @NonNull
  private String name;

  @Indexed(//
      schemaFieldType = SchemaFieldType.VECTOR, //
      algorithm = VectorAlgo.HNSW, //
      type = VectorType.FLOAT32, //
      dimension = 512, //
      distanceMetric = DistanceMetric.L2, //
      initialCapacity = 10
  )
  private byte[] imageEmbedding;

  @Vectorize(destination = "imageEmbedding", embeddingType = EmbeddingType.IMAGE)
  @NonNull
  private String imagePath;

  @Indexed(//
      schemaFieldType = SchemaFieldType.VECTOR, //
      algorithm = VectorAlgo.HNSW, //
      type = VectorType.FLOAT32, //
      dimension = 768, //
      distanceMetric = DistanceMetric.L2, //
      initialCapacity = 10
  )
  private byte[] sentenceEmbedding;

  @Vectorize(destination = "sentenceEmbedding", embeddingType = EmbeddingType.SENTENCE)
  @NonNull
  private String description;
}
