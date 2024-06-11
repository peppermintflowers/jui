package com.peppermintflowers.poc.dashboard.config;

import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongoConfig {
    @Value("spring.data.mongodb.name")
    String dbname;
    @Value("spring.data.mongodb.uri")
    String connectionString;
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(MongoClients.create(connectionString), dbname);
    }
}
