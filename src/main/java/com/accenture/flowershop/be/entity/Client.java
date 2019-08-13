package com.accenture.flowershop.be.entity;

import com.accenture.flowershop.fe.enums.ClientRole;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "client")
@Entity
public class Client implements Serializable {

    @Id
    private String login;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private ClientRole role;
    private String lName;
    private String fName;
    private String mName;
    private String address;
    private String phoneNumber;
    @Column(nullable = false)
    private BigDecimal balance = new BigDecimal(2000.00);
    @Column(nullable = false)
    private Integer discount = 5;
    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name = "id")
    private List<Purchase> purchaseList = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY)
    private List<BasketItem> basketItemList = new ArrayList<>();

    public Client(){}

    public Client(String login, String password, String lName, String fName, String mName, String address, String phoneNumber) {
        this.login = login;
        this.password = password;
        this.lName = lName;
        this.fName = fName;
        this.mName = mName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ClientRole getRole() {
        return role;
    }

    public void setRole(ClientRole role) {
        this.role = role;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public List<Purchase> getPurchaseList() {
        return purchaseList;
    }

    public void setPurchaseList(List<Purchase> purchaseList) {
        this.purchaseList = purchaseList;
    }

    public List<BasketItem> getBasketItemList() {
        return basketItemList;
    }

    public void setBasketItemList(List<BasketItem> basketItemList) {
        this.basketItemList = basketItemList;
    }
}
