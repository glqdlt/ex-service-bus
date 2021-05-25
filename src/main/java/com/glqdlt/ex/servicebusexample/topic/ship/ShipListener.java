package com.glqdlt.ex.servicebusexample.topic.ship;

import com.glqdlt.ex.servicebusexample.topic.AbstractListner;
import com.microsoft.azure.servicebus.IMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author glqdlt
 */
@Component
public class ShipListener extends AbstractListner {
    private final static Logger LOGGER = LoggerFactory.getLogger(ShipListener.class);

    public String getAliasName() {
        return "SHIP";
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }


    public void job(IMessage message) {
        LOGGER.info("SHIP CALLED");
    }
}
