package com.glqdlt.ex.servicebusexample.topic.order;

import com.glqdlt.ex.servicebusexample.topic.EventIdentity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author glqdlt
 */
public class OrderEntity implements EventIdentity {

    private String id;
    private LocalDateTime createTime;
    private String description;
    private String writer;


    public LocalDateTime getCreateTime() {
        return createTime;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static OrderEntity valueOf(String writer,
                                      String description) {
        OrderEntity order
                = new OrderEntity();
        order.setId(UUID.randomUUID().toString());
        order.setCreateTime(LocalDateTime.now());
        order.setDescription(description);
        order.setWriter(writer);
        return order;
    }
}
