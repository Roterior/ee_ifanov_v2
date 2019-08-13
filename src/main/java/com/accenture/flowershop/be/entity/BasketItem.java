package com.accenture.flowershop.be.entity;

import com.accenture.flowershop.fe.enums.BasketItemStatus;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "basketitem_id")
    @SequenceGenerator(name = "basketitem_id", sequenceName = "BASKETITEM_ID_SEQ", allocationSize = 1)
    private Long id;
    private Integer quantityToBuy;
    private BigDecimal sum;
    @Enumerated(EnumType.STRING)
    private BasketItemStatus status;
    @ManyToOne(fetch = FetchType.EAGER)
    private Flower flower;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantityToBuy() {
        return quantityToBuy;
    }

    public void setQuantityToBuy(Integer quantityToBuy) {
        this.quantityToBuy = quantityToBuy;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }

    public BasketItemStatus getStatus() {
        return status;
    }

    public void setStatus(BasketItemStatus status) {
        this.status = status;
    }
}
