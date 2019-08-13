package com.accenture.flowershop.fe.enums;

public enum PurchaseStatus {

    CREATED("CRT"),
    PAID("PAD"),
    CLOSED("CLS");

    private String name;

    PurchaseStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }
}
