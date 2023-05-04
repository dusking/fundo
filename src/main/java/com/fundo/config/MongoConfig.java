package com.fundo.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableMongoRepositories(basePackages = "com.fundo.api")
public class MongoConfig {

    @Autowired
    private AppConfig appDetails;

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
        String databaseName = "test";
        return new MongoTemplate(mongo(), databaseName);
    }

    public String getMongoConnectionUri() {
        String username = System.getenv("MONGO_USERNAME");
        String password = System.getenv("MONGO_PASSWORD");
        String host = "cluster0.qzji9ua.mongodb.net";
        String arguments = "retryWrites=true&w=majority";
        return String.format("mongodb+srv://%s:%s@%s/?%s", username, password, host, arguments);
    }
}