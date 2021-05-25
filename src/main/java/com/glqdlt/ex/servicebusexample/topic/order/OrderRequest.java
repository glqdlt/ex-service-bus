package com.glqdlt.ex.servicebusexample.topic.order;

import java.time.LocalDateTime;

/**
 * @author glqdlt
 */
public class OrderRequest {

    private Integer itemId;
    private Integer count;
    private LocalDateTime requestTime;
    private String userId;
    private Long requestOrderPayment;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getRequestOrderPayment() {
        return requestOrderPayment;
    }

    public void setRequestOrderPayment(Long requestOrderPayment) {
        this.requestOrderPayment = requestOrderPayment;
    }
}
