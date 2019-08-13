package com.accenture.flowershop.be.entity;

import com.accenture.flowershop.fe.enums.PurchaseStatus;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Entity
public class Purchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_id")
    @SequenceGenerator(name = "purchase_id", sequenceName = "PURCHASE_ID_SEQ", allocationSize = 1)
    private Long id;
    private BigDecimal totalPrice;
    private LocalDate createDate;
    private LocalDate closeDate;
    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;
    private Currency currency;
    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "CLIENT_LOGIN")
    private Client client;
    @OneToMany(fetch = FetchType.LAZY)
    private List<BasketItem> basketItemList = new ArrayList<>();

    public Purchase(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public PurchaseStatus getStatus() {
        return status;
    }

    public void setStatus(PurchaseStatus status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<BasketItem> getBasketItemList() {
        return basketItemList;
    }

    public void setBasketItemList(List<BasketItem> basketItemList) {
        this.basketItemList = basketItemList;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
