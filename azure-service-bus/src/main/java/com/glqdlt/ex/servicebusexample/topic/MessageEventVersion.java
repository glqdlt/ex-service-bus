package com.glqdlt.ex.servicebusexample.topic;

import java.time.LocalDateTime;

/**
 * @author glqdlt
 */
public class MessageEventVersion implements EventVersion<String> {
    private String eventId;
    private Integer version;
    private LocalDateTime createTime;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    @Override
    public String getDetail() {
        return getMessage();
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
