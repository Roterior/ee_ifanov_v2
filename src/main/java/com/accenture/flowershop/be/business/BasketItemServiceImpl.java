package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.access.BasketItemDAO;
import com.accenture.flowershop.be.entity.BasketItem;
import com.accenture.flowershop.fe.enums.BasketItemStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BasketItemServiceImpl implements BasketItemService {

    private final BasketItemDAO basketItemDAO;

    @Autowired
    public BasketItemServiceImpl(BasketItemDAO basketItemDAO) {
        this.basketItemDAO = basketItemDAO;
    }

    @Transactional
    @Override
    public BasketItem add(BasketItem basketItem) {
        try {
            basketItem.setStatus(BasketItemStatus.WAITING);
            return basketItemDAO.add(basketItem);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Transactional
    @Override
    public BasketItem remove(Long id) {
        try {
            BasketItem basketItem = basketItemDAO.getById(id);
            basketItemDAO.remove(basketItem);
            return basketItem;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Override
    public BasketItem getById(Long id) {
        try {
            return basketItemDAO.getById(id);
        }
        catch (Exception e) {
            return null;
        }
    }

    @Transactional
    @Override
    public BasketItem updateQuantity(Long id, Integer quantity) {
        try {
            BasketItem basketItem = basketItemDAO.getById(id);
            Integer quantityTemp = basketItem.getQuantityToBuy() + quantity;
            basketItem.setQuantityToBuy(quantityTemp);
            basketItemDAO.update(basketItem);
            return basketItem;
        }
        catch (Exception e) {
            return null;
        }
    }

    @Transactional
    @Override
    public BasketItem updateStatus(Long id, BasketItemStatus status) {
        try {
            BasketItem basketItem = basketItemDAO.getById(id);
            basketItem.setStatus(status);
            return basketItem;
        }
        catch (Exception e) {
            return null;
        }
    }
}
