package coms.glqdlt.ex.springevent.ship;

import coms.glqdlt.ex.springevent.EventBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author glqdlt
 */
@Component
public class ShipEventListener implements ApplicationListener<EventBase> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShipEventListener.class);

    @Override
    public void onApplicationEvent(EventBase event) {
        if (event.getLabel().equals("ship")) {
            LOGGER.info("call ship event {}", event.toString());
        } else {
            LOGGER.info("call another event {}", event.toString());
        }
    }
}
