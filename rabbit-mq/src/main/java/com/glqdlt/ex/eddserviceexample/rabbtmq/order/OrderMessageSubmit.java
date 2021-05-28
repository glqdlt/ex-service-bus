package com.glqdlt.ex.eddserviceexample.rabbtmq.order;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author glqdlt
 */
@Component
public class OrderMessageSubmit {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final ExecutorService pool = Executors.newFixedThreadPool(2);
    @Autowired
    public void hello() {
        pool.submit(() -> {
        while(true){
        rabbitTemplate.convertSendAndReceive("amqp.topic","foo.bar.1","hello rabbit");
            Thread.sleep(TimeUnit.SECONDS.toMillis(1L));
        }
        });
    }

}
