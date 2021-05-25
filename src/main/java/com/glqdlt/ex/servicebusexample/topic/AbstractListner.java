package com.glqdlt.ex.servicebusexample.topic;

import com.microsoft.azure.servicebus.*;
import com.microsoft.azure.servicebus.management.ManagementClient;
import com.microsoft.azure.servicebus.management.SubscriptionDescription;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import com.microsoft.azure.servicebus.primitives.MessagingEntityAlreadyExistsException;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author glqdlt
 */
public abstract class AbstractListner implements IMessageHandler {
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
    public CompletableFuture<Void> onMessageAsync(IMessage message) {
        return CompletableFuture.runAsync(() -> {
            if (message.getReplyTo().equals(this.getAliasName())) {
                job(message);
            }
            subscriptionClient.completeAsync(message.getLockToken());
        });
    }

    public abstract void job(IMessage message);

    @Override
    public void notifyException(Throwable exception, ExceptionPhase phase) {

    }
}
