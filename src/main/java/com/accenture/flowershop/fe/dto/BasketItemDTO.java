package com.accenture.flowershop.fe.dto;

import java.math.BigDecimal;

public class BasketItemDTO {

    private Long id;
    private Integer quantityToBuy;
    private BigDecimal sum;
    private FlowerDTO flower;

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

    public FlowerDTO getFlower() {
        return flower;
    }

    public void setFlower(FlowerDTO flower) {
        this.flower = flower;
    }
}
