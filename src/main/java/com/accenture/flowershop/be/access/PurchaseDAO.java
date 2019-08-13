package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.Purchase;
import java.util.List;

public interface PurchaseDAO {

    Purchase add(Purchase purchase);

    List<Purchase> getAll();

    List<Purchase> getByLogin(String login);

    Purchase getById(Long id);

    Purchase update(Purchase purchase);

}
