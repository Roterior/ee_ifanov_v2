package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.entity.Purchase;
import com.accenture.flowershop.fe.dto.PurchaseDTO;
import com.accenture.flowershop.fe.enums.PurchaseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface PurchaseService {

    Purchase add(Purchase purchase);

    List<Purchase> getAll();

    Purchase getById(Long id);

    List<Purchase> getByLogin(String login);

    Purchase updateCloseDateAndStatus(Long id);

    Purchase updateStatus(Long id, PurchaseStatus status);

    void onOrderButton(HttpServletRequest req, HttpServletResponse resp, PurchaseDTO purchaseDTO);

    void onPayButton(HttpServletRequest req, HttpServletResponse resp, PurchaseDTO purchaseDTO);

}
