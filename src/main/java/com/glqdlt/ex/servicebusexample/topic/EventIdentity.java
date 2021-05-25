package com.glqdlt.ex.servicebusexample.topic;

import java.time.LocalDateTime;

public interface EventIdentity {
    String getId();

    LocalDateTime getCreateTime();

    String getDescription();

    String getWriter();
}
