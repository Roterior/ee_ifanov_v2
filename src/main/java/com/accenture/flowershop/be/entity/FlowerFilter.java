package com.accenture.flowershop.be.entity;

import java.math.BigDecimal;

public class FlowerFilter {

    private String name;
    private BigDecimal fromPrice;
    private BigDecimal toPrice;

    public FlowerFilter() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getFromPrice() {
        return fromPrice;
    }

    public void setFromPrice(BigDecimal fromPrice) {
        this.fromPrice = fromPrice;
    }

    public BigDecimal getToPrice() {
        return toPrice;
    }

    public void setToPrice(BigDecimal toPrice) {
        this.toPrice = toPrice;
    }
}
