package com.glqdlt.ex.eddserviceexample.rabbtmq.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author glqdlt
 */
@Component
public class OrderListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderListener.class);
    @RabbitListener(queues = "test")
    public void receiveMessage(final Message message) {
        LOGGER.info(new String(message.getBody()));
    }
}
