package coms.glqdlt.ex.springevent.payment;

import coms.glqdlt.ex.springevent.ship.ShipEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author glqdlt
 */
@Component
public class PaymentListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentListener.class);

    @EventListener(value = {PaymentEvent.class, ShipEvent.class})
    public void aaa(Object paymentEvent) {
        LOGGER.info(paymentEvent.toString());
    }
}
