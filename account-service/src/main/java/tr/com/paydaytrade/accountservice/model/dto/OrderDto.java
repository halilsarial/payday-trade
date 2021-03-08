package tr.com.paydaytrade.accountservice.model.dto;

import tr.com.paydaytrade.accountservice.model.OrderType;

public class OrderDto {

    private Long accountId;
    private Long size;
    private String ticker;
    private OrderType orderType;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
}
