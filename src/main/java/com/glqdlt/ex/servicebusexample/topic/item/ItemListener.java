package com.glqdlt.ex.servicebusexample.topic.item;

import com.glqdlt.ex.servicebusexample.topic.AbstractListner;
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
public class ItemListener extends AbstractListner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemListener.class);

    @Override
    public String getAliasName() {
        return "ITEM";
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public void job(IMessage message) {
        DefaultEvent ccc = message.getMessageBody().getBinaryData()
                .stream()
                .map(x -> this.convertValue(x, DefaultEvent.class))
                .findFirst()
                .get();

        LOGGER.info("ITEM receive : {}", ccc.getId());
        LOGGER.info("ITEM SALE COMPLETE : {}", ccc.getId());
        Message a = new Message(MessageBody.fromBinaryData(Collections.singletonList(convertJson(ccc))));
        a.setLabel("request");
        a.setReplyTo("PAY");
        getTopicClient().sendAsync(a);
    }

    @Override
    public void rollback(IMessage iMessage) {
        DefaultEvent ccc = iMessage.getMessageBody().getBinaryData()
                .stream()
                .map(x -> this.convertValue(x, DefaultEvent.class))
                .findFirst()
                .get();
        LOGGER.info("ITEM ROLLBACK {}", ccc.getId());
    }
}
