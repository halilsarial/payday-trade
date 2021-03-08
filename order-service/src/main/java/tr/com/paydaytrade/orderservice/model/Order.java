package tr.com.paydaytrade.orderservice.model;

import javax.persistence.*;

@Entity
@Table
public class Order extends BaseEntity {

    @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "account_id")
    private Account account;

    @Column
    private orderStatus status;

    @Column
    private Integer size;

    @Column
    private Double price;

    @Column
    private String notes;

    @Column
    private String ticker;

    public orderStatus getStatus() {
        return status;
    }

    public void setStatus(orderStatus status) {
        this.status = status;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public enum orderStatus {
        FILLED, CANCELLED, PENDING
    }
}