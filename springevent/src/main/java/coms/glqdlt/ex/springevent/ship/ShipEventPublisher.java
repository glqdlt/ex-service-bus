package coms.glqdlt.ex.springevent.ship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author glqdlt
 */
@Component
public class ShipEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void aaa() {
        applicationEventPublisher.publishEvent(new ShipEvent("hi", UUID.randomUUID().toString()));
    }

}
