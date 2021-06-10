package coms.glqdlt.ex.springevent.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author glqdlt
 */
@Component
public class PaymentEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void aaa() {
        applicationEventPublisher.publishEvent(new PaymentEvent("hello", UUID.randomUUID().toString()));
    }

}
