package com.glqdlt.ex.servicebusexample.topic.order;

import com.glqdlt.ex.servicebusexample.topic.AbstractAzureBusListener;
import com.glqdlt.ex.servicebusexample.topic.DefaultEvent;
import com.microsoft.azure.servicebus.IMessage;
import com.microsoft.azure.servicebus.Message;
import com.microsoft.azure.servicebus.MessageBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author glqdlt
 */
@Component
public class OrderListener extends AbstractAzureBusListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderListener.class);

    @Override
    public String getAliasName() {
        return "ORDER";
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public void job(IMessage message) {
        String command = message.getLabel();
        if (command.equals("1")) {
            DefaultEvent ccc = message.getMessageBody().getBinaryData()
                    .stream()
                    .map(x -> this.convertValue(x, DefaultEvent.class))
                    .findFirst()
                    .get();

            LOGGER.info("order receive REQUEST : {}, {}", ccc.getId(), message.getMessageId());
            Message a = new Message(MessageBody.fromBinaryData(Collections.singletonList(convertJson(ccc))));
            a.setReplyTo("ITEM");
            a.setLabel("REQUEST");
            getTopicClient().sendAsync(a);
        } else if (command.equals("2")) {
            DefaultEvent ccc = message.getMessageBody().getBinaryData()
                    .stream()
                    .map(x -> this.convertValue(x, DefaultEvent.class))
                    .findFirst()
                    .get();

            LOGGER.info("order receive 2 : {}", ccc.getId());
            Message a = new Message(MessageBody.fromBinaryData(Collections.singletonList(convertJson(ccc))));
            a.setReplyTo("SHIP");
            a.setLabel("REQUEST");
            getTopicClient().sendAsync(a);
        } else if (command.equals("3")) {
            DefaultEvent ccc = message.getMessageBody().getBinaryData()
                    .stream()
                    .map(x -> this.convertValue(x, DefaultEvent.class))
                    .findFirst()
                    .get();

            LOGGER.info("order receive 3 : {}", ccc.getId());
            LOGGER.info("FINISH ORDER");
        }

    }

    @Override
    public void rollback(IMessage iMessage) {
        DefaultEvent ccc = iMessage.getMessageBody().getBinaryData()
                .stream()
                .map(x -> this.convertValue(x, DefaultEvent.class))
                .findFirst()
                .get();
        LOGGER.info("ORDER ROLLBACK {}",ccc.getId());
    }
}
