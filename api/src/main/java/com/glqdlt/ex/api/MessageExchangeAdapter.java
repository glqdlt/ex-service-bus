package com.glqdlt.ex.api;

public interface MessageExchangeAdapter<RESPONSE,EVENT, ERROR> {

    RESPONSE onListen(EVENT event);

    void onError(ERROR E);
}
