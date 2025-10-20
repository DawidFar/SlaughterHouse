package com.slaughterhouse.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        // Read DB settings from environment variables or use defaults
        String host = System.getenv().getOrDefault("DB_HOST", "localhost");
        String port = System.getenv().getOrDefault("DB_PORT", "5432");
        String dbName = System.getenv().getOrDefault("DB_NAME", "slaughterhouse");
        String dbUser = System.getenv().getOrDefault("DB_USER", "postgres");
        String dbPass = System.getenv().getOrDefault("DB_PASS", "postgres");
        String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName);

        Database db = new Database(jdbcUrl, dbUser, dbPass);

        int grpcPort = Integer.parseInt(System.getenv().getOrDefault("GRPC_PORT", "9090"));

        Server server = ServerBuilder.forPort(grpcPort)
                .addService(new SlaughterhouseServiceImpl(db))
                .build()
                .start();

        System.out.println("gRPC server started on port " + grpcPort);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server...");
            try {
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            server.shutdown();
        }));

        server.awaitTermination();
    }
}
