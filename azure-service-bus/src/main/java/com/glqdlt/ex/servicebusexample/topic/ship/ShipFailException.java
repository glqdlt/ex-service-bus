package com.glqdlt.ex.servicebusexample.topic.ship;

import com.glqdlt.ex.servicebusexample.topic.DefaultEvent;

/**
 * @author glqdlt
 */
public class ShipFailException extends RuntimeException {

    public ShipFailException(DefaultEvent eventId) {
        this.eventId = eventId;
    }

    private DefaultEvent eventId;


    public DefaultEvent getEventId() {
        return eventId;
    }
}
