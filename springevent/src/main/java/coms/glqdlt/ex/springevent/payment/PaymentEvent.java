package coms.glqdlt.ex.springevent.payment;

import coms.glqdlt.ex.springevent.EventBase;

/**
 * @author glqdlt
 */
public class PaymentEvent extends EventBase {

    public PaymentEvent(String source, String id) {
        super(source, "ship", id);
    }
}
