package coms.glqdlt.ex.springevent.ship;

import coms.glqdlt.ex.springevent.EventBase;

/**
 * @author glqdlt
 */
public class ShipEvent extends EventBase {
    public ShipEvent(String source, String id) {
        super(source, "ship", id);
    }
}
