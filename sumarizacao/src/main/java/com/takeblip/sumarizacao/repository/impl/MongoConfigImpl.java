package com.takeblip.sumarizacao.repository.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.takeblip.sumarizacao.repository.ConversasRepository")
public class MongoConfigImpl extends AbstractMongoClientConfiguration {
    @Override
    protected String getDatabaseName() {
        return "takeblip";
    }

    @Override
    public MongoClient mongoClient() {
        return MongoClients.create();
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return  new MongoTemplate(mongoClient(), getDatabaseName());
    }
}
