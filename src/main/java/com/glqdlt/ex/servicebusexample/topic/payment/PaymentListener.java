package com.glqdlt.ex.servicebusexample.topic.payment;

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
public class PaymentListener extends AbstractListner {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentListener.class);

    @Override
    public String getAliasName() {
        return "PAY";
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

        LOGGER.info("PAY receive : {}, {}", ccc.getId(), message.getMessageId());
        LOGGER.info("PAY COMPLETE : {}", ccc.getId());
        Message a = new Message(MessageBody.fromBinaryData(Collections.singletonList(convertJson(ccc))));
        a.setLabel("2");
        a.setReplyTo("ORDER");
        getTopicClient().sendAsync(a);
    }

    @Override
    public void rollback(IMessage iMessage) {
        DefaultEvent ccc = iMessage.getMessageBody().getBinaryData()
                .stream()
                .map(x -> this.convertValue(x, DefaultEvent.class))
                .findFirst()
                .get();
        LOGGER.info("PAY ROLLBACk {}", ccc.getId());
    }
}
