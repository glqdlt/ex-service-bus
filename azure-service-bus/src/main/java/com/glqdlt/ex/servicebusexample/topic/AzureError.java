package com.glqdlt.ex.servicebusexample.topic;

import com.microsoft.azure.servicebus.ExceptionPhase;

/**
 * @author glqdlt
 */
public class AzureError {
    private Throwable throwable;
    private ExceptionPhase exceptionPhase;

    public Throwable getThrowable() {
        return throwable;
    }


    public ExceptionPhase getExceptionPhase() {
        return exceptionPhase;
    }

    public AzureError(Throwable throwable, ExceptionPhase exceptionPhase) {
        this.throwable = throwable;
        this.exceptionPhase = exceptionPhase;
    }
}
