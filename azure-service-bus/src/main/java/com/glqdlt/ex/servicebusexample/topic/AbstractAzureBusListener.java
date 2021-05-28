package com.glqdlt.ex.servicebusexample.topic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.servicebus.*;
import com.microsoft.azure.servicebus.management.ManagementClient;
import com.microsoft.azure.servicebus.management.SubscriptionDescription;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import com.microsoft.azure.servicebus.primitives.MessagingEntityAlreadyExistsException;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * @author glqdlt
 */
public abstract class AbstractAzureBusListener implements AzureAdapter {
    @Autowired
    private TopicClient topicClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public <V> V convertValue(byte[] data, Class<V> v) {
        String json = new String(data);
        try {
            return getObjectMapper().readValue(data, v);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] convertJson(Object object) {
        try {
            return getObjectMapper().writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public TopicClient getTopicClient() {
        return topicClient;
    }

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private SubscriptionClient subscriptionClient;

    public abstract String getAliasName();

    public abstract org.slf4j.Logger getLogger();

    @Autowired
    public void topicClientOutput(
            @Value("${topic.name}") String topicName,
            @Value("${topic.con.output}") String connectionString) throws ServiceBusException, InterruptedException {
        SubscriptionDescription subscriptionDescription = new SubscriptionDescription(topicName, getAliasName());
        subscriptionDescription.setAutoDeleteOnIdle(Duration.ofDays(14));
        try {
            ManagementClient mng = new ManagementClient(new ConnectionStringBuilder(connectionString));
            mng.createSubscription(subscriptionDescription);
        } catch (MessagingEntityAlreadyExistsException e) {
            getLogger().warn(e.getMessage());
        }
        SubscriptionClient subscriptionClient = new SubscriptionClient(new ConnectionStringBuilder(connectionString, topicName + "/subscriptions/" + subscriptionDescription.getSubscriptionName()), ReceiveMode.PEEKLOCK);
        subscriptionClient.registerMessageHandler(this, new MessageHandlerOptions(1, false, Duration.ofMinutes(1)), executorService);
        this.subscriptionClient = subscriptionClient;
    }

    public final SubscriptionClient getSubscriptionClient() {
        return this.subscriptionClient;
    }


    @Override
    public CompletableFuture<Void> onListen(IMessage message) {
        return CompletableFuture.runAsync(() -> {
            boolean isCalled = checkReplyTo(message.getReplyTo());
            if (isCalled) {
                if (message.getLabel().equals("rollback")) {
                    rollback(message);
                } else {
                    job(message);
                }
            }
            subscriptionClient.completeAsync(message.getLockToken());
        });
    }

    private boolean checkReplyTo(String replyTo) {
        if (replyTo == null || replyTo.equals("")) {
            return false;
        }
        String[] rr = replyTo.split(",");
        return Stream.of(rr).anyMatch(x -> x.equals(getAliasName()) || x.equals("all"));
    }

    public abstract void job(IMessage message);

    public abstract void rollback(IMessage iMessage);

    @Override
    public void onError(AzureError e) {


    }
}
