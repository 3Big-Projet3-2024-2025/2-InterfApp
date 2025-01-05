package be.helha.interf_app;

import be.helha.interf_app.config.MongoConfig;
import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link MongoConfig}.
 * Verifies that the MongoDB configuration is correctly set up and functional.
 */
@SpringBootTest
@ContextConfiguration(classes = MongoConfig.class) // Load MongoConfig for testing
public class MongoConfigTest {

    @Autowired
    private MongoConfig mongoConfig;

    @Autowired
    private MongoClient mongoClient;

    /**
     * Tests if the database name is correctly configured.
     */
    @Test
    void testDatabaseName() {
        // Check that the database name is correctly configured
        String databaseName = mongoConfig.getDatabaseName();
        assertEquals("interfApp", databaseName, "The database name should be 'interfApp'");
    }

    /**
     * Tests if the {@link MongoClient} bean is correctly initialized.
     */
    @Test
    void testMongoClientNotNull() {
        // Ensure the MongoClient bean is correctly initialized
        assertNotNull(mongoClient, "MongoClient should not be null");
    }

    /**
     * Tests if the {@link MongoClient} connects to the expected database.
     */
    @Test
    void testMongoClientConnection() {
        // Test if MongoClient can connect to the expected database
        var database = mongoClient.getDatabase(mongoConfig.getDatabaseName());
        assertNotNull(database, "Database object should not be null");
        assertEquals("interfApp", database.getName(), "Database name should match the configuration");
    }
}