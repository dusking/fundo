package com.fundo.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "com.fundo.api")
public class MongoConfig {

    @Autowired
    private Environment env;

    @Bean
    public MongoClient mongo() {
        ConnectionString connectionString = new ConnectionString(getMongoConnectionUri());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        String databaseName = env.getProperty("spring.mongo.dbname");
        return new MongoTemplate(mongo(), databaseName);
    }

    public String getMongoConnectionUri() {
        String username = env.getProperty("spring.mongo.username");
        String password = env.getProperty("spring.mongo.password");
        String host = env.getProperty("spring.mongo.host");
        String arguments = env.getProperty("spring.mongo.arguments");
        return String.format("mongodb+srv://%s:%s@%s/?%s", username, password, host, arguments);
    }
}