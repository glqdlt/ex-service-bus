package com.glqdlt.ex.servicebusexample.topic.payment;

import com.glqdlt.ex.servicebusexample.topic.AbstractListner;
import com.microsoft.azure.servicebus.IMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
        getLogger().info("CALL PAY");
    }
}
