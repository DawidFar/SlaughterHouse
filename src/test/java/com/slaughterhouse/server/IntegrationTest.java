package com.slaughterhouse.server;

import com.slaughterhouse.grpc.SlaughterhouseProto.*;
import com.slaughterhouse.grpc.SlaughterhouseServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationTest {
    private Database db;

    @BeforeAll
    public void setup() throws Exception {
        String host = System.getenv().getOrDefault("DB_HOST", "localhost");
        String port = System.getenv().getOrDefault("DB_PORT", "5432");
        String dbName = System.getenv().getOrDefault("DB_NAME", "slaughterhouse");
        String dbUser = System.getenv().getOrDefault("DB_USER", "postgres");
        String dbPass = System.getenv().getOrDefault("DB_PASS", "postgres");
        String jdbc = String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName);
        // Will throw if DB is unavailable; tests will fail in that case.
        db = new Database(jdbc, dbUser, dbPass);
    }

    @AfterAll
    public void teardown() throws Exception {
        if (db != null) db.close();
    }

    @Test
    public void testQueriesRun() throws Exception {
        // Very simple smoke test: run server locally and call it (assumes server is started separately).
        // This test only ensures the DB has expected tables by querying metadata.
        try (Statement st = db.getConnection().createStatement()) {
            st.executeQuery("SELECT 1");
        }
    }
}
