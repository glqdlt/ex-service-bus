package com.glqdlt.ex.servicebusexample.topic.ship;

import com.glqdlt.ex.servicebusexample.topic.AbstractListner;
import com.glqdlt.ex.servicebusexample.topic.DefaultEvent;
import com.microsoft.azure.servicebus.ExceptionPhase;
import com.microsoft.azure.servicebus.IMessage;
import com.microsoft.azure.servicebus.Message;
import com.microsoft.azure.servicebus.MessageBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author glqdlt
 */
@Component
public class ShipListener extends AbstractListner {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShipListener.class);

    public String getAliasName() {
        return "SHIP";
    }

    @Autowired
    private AtomicInteger atomicInteger;

    @Override
    public Logger getLogger() {
        return LOGGER;
    }


    public void job(IMessage message) {
        DefaultEvent ccc = message.getMessageBody().getBinaryData()
                .stream()
                .map(x -> this.convertValue(x, DefaultEvent.class))
                .findFirst()
                .get();

        LOGGER.info("SHIP RECIEVE : {}", ccc.getId());
        LOGGER.info("SHIP SUBMIT: {}", ccc.getId());

        Message a = new Message(MessageBody.fromBinaryData(Collections.singletonList(convertJson(ccc))));
        a.setLabel("3");
        a.setReplyTo("ORDER");
        getTopicClient().sendAsync(a);
        int aa = atomicInteger.getAndAdd(1);
        if (aa > 4) {
            throw new ShipFailException(ccc);
        }
    }

    @Override
    public void rollback(IMessage message) {
        DefaultEvent ccc = message.getMessageBody().getBinaryData()
                .stream()
                .map(x -> this.convertValue(x, DefaultEvent.class))
                .findFirst()
                .get();
        LOGGER.info("SHIP ROLLBACK {}", ccc.getId());
    }

    @Override
    public void notifyException(Throwable exception, ExceptionPhase phase) {
        LOGGER.error(exception.getMessage(), exception);
        if (exception instanceof ShipFailException) {
            DefaultEvent aa = ((ShipFailException) exception).getEventId();
            LOGGER.info("SHIP ERROR!!! .. ROLLBACk..");
            Message message
                    = new Message();
            message.setLabel("rollback");
            message.setReplyTo("all");
            message.setMessageBody(MessageBody.fromBinaryData(Arrays.asList(this.convertJson(aa))));
            getTopicClient().sendAsync(message);
        }
    }
}
