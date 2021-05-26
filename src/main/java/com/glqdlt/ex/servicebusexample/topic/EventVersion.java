package com.glqdlt.ex.servicebusexample.topic;

import java.time.LocalDateTime;

public interface EventVersion<V> {
    String getEventId();

    Integer getVersion();

    LocalDateTime getCreateTime();

    V getDetail();
}
