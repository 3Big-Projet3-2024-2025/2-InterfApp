package be.helha.interf_app.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

/**
 * Configuration class for connecting to a MongoDB database.
 * This class sets up the MongoDB client and defines the database name to be used.
 * It extends {@link AbstractMongoClientConfiguration} to leverage Spring Data MongoDB configurations.
 */
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    /**
     * Specifies the name of the MongoDB database to be used.
     *
     * @return the name of the database, "interfApp".
     */
    @Override
    public String getDatabaseName(){
        return "interfApp"; // Name of the database
    }

    /**
     * Creates and configures a {@link MongoClient} bean for connecting to MongoDB.
     *
     * @return a {@link MongoClient} instance pointing to "mongodb://localhost:27017".
     */
    @Bean
    @Override
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017"); // Connection to MongoDb
    }
}
