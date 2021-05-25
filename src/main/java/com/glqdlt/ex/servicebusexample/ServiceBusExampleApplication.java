package com.glqdlt.ex.servicebusexample;

import com.microsoft.azure.servicebus.*;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SpringBootApplication
public class ServiceBusExampleApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceBusExampleApplication.class);
    @Value("${servicebus.con}")
    private String connectionString;

    public static void main(String[] args) {
        SpringApplication.run(ServiceBusExampleApplication.class, args);
    }

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(4);

    @Override
    public void run(String... args) throws Exception {
        QueueClient receiveClient = new QueueClient(new ConnectionStringBuilder(connectionString, "basictopic2"), ReceiveMode.PEEKLOCK);

        receiveClient.registerMessageHandler(new IMessageHandler() {
            @Override
            public CompletableFuture<Void> onMessageAsync(IMessage iMessage) {
                return CompletableFuture.runAsync(() -> {
                    UUID token = iMessage.getLockToken();
                    String body = new String(iMessage.getBody());
                    LOGGER.info(body);
                    LOGGER.info("count {}", iMessage.getDeliveryCount());

                    if (iMessage.getDeliveryCount() >= 10) {
                        receiveClient.deadLetterAsync(token, "error001", "network error, need manual submit.");
                        return;
                    }
                    if (body.startsWith("1")) {
                        receiveClient.abandonAsync(token);
                    } else {
                        receiveClient.completeAsync(token);
                    }
                });
            }

            @Override
            public void notifyException(Throwable throwable, ExceptionPhase exceptionPhase) {
                LOGGER.warn(exceptionPhase.name());
                LOGGER.warn(throwable.getMessage(), throwable);
            }
        }, new MessageHandlerOptions(1, false, Duration.ofMinutes(1)), EXECUTOR);
    }
}
