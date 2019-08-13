package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.entity.BasketItem;
import com.accenture.flowershop.fe.enums.BasketItemStatus;

public interface BasketItemService {

    BasketItem add(BasketItem basketItem);

    BasketItem remove(Long id);

    BasketItem getById(Long id);

    BasketItem updateQuantity(Long id, Integer quantity);

    BasketItem updateStatus(Long id, BasketItemStatus status);

}
