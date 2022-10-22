package com.lightbulb.server;

import com.lightbulb.core.logging.Logger;
import com.lightbulb.core.logging.LoggerFactory;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LightbulbServer {
    private static final Logger logger = LoggerFactory.getLogger(LightbulbServer.class);
    private static final int PORT = 5123;
    private Server server;

    public void start() throws IOException {
        server = ServerBuilder.forPort(PORT)
                .build()
                .start();
        logger.info("Server started, listening on " + PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                LightbulbServer.this.stop();
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
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
