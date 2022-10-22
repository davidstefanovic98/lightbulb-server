package com.lightbulb.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class LightbulbServer {
    private static final Logger logger = Logger.getLogger(LightbulbServer.class.getName());
    private static final int PORT = 5123;
    private Server server;

    public void start() throws IOException {
        server = ServerBuilder.forPort(PORT)
                .build()
                .start();
        logger.info("Server started, listening on " + PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Received shutdown request");
            try {
                LightbulbServer.this.stop();
            } catch (InterruptedException e) {
                logger.severe(e.getMessage());
            }
            logger.info("Server stopped");
        }));
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
