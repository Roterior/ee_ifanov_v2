package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.BasketItem;

public interface BasketItemDAO {

    BasketItem getById(Long id);

    BasketItem add(BasketItem basketItem);

    BasketItem remove(BasketItem basketItem);

    BasketItem update(BasketItem basketItem);

}
