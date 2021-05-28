package com.glqdlt.ex.servicebusexample.topic;

import com.glqdlt.ex.api.MessageExchangeAdapter;
import com.microsoft.azure.servicebus.ExceptionPhase;
import com.microsoft.azure.servicebus.IMessage;
import com.microsoft.azure.servicebus.IMessageHandler;

import java.util.concurrent.CompletableFuture;

/**
 * @author glqdlt
 */
public interface AzureAdapter extends IMessageHandler, MessageExchangeAdapter<CompletableFuture<Void>, IMessage, AzureError> {

    @Override
    default CompletableFuture<Void> onMessageAsync(IMessage iMessage) {
        return onListen(iMessage);
    }

    @Override
    default void notifyException(Throwable throwable, ExceptionPhase exceptionPhase) {
        onError(new AzureError(throwable,exceptionPhase));
    }
}
