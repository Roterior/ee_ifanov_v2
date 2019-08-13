package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.BasketItem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class BasketItemImpl implements BasketItemDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public BasketItem add(BasketItem basketItem) {
        em.persist(basketItem);
        return basketItem;
    }

    @Transactional
    @Override
    public BasketItem remove(BasketItem basketItem) {
        em.remove(basketItem);
        return basketItem;
    }

    @Override
    public BasketItem getById(Long id) {
        TypedQuery<BasketItem> q = em.createQuery("SELECT b FROM BasketItem b WHERE b.id = :id", BasketItem.class);
        q.setParameter("id", id);
        return q.getSingleResult();
    }

    @Transactional
    @Override
    public BasketItem update(BasketItem basketItem) {
        em.merge(basketItem);
        return basketItem;
    }
}
