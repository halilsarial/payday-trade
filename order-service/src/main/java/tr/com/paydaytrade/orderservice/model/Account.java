package tr.com.paydaytrade.orderservice.model;

import javax.persistence.*;

@Entity
@Table
public class Account extends BaseEntity {

    @OneToOne(targetEntity = TraderUser.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "trader_user_id")
    private TraderUser traderUser;

    @Column
    private Double amount;

    public TraderUser getTraderUser() {
        return traderUser;
    }

    public void setTraderUser(TraderUser traderUser) {
        this.traderUser = traderUser;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
