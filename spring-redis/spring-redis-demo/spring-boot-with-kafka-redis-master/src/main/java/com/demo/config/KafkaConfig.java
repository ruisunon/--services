package com.demo.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("employer")
                .partitions(5)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("employee")
                .partitions(5)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic topic3() {
        return TopicBuilder.name("adminUser")
                .partitions(5)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic topic4() {
        return TopicBuilder.name("categories")
                .partitions(5)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic topic5() {
        return TopicBuilder.name("Jobs")
                .partitions(5)
                .replicas(2)
                .build();
    }
}
