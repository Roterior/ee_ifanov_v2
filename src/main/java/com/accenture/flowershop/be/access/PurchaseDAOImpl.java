package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.Purchase;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PurchaseDAOImpl implements PurchaseDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Purchase add(Purchase purchase) {
        em.persist(purchase);
        return purchase;
    }

    @Override
    public List<Purchase> getAll() {
        TypedQuery<Purchase> q = em.createQuery("SELECT p FROM Purchase p ORDER BY p.createDate, p.status DESC", Purchase.class);
        return q.getResultList();
    }

    @Override
    public List<Purchase> getByLogin(String login) {
        TypedQuery<Purchase> q = em.createQuery("SELECT p FROM Purchase p WHERE p.client.login = :clientLogin", Purchase.class);
        q.setParameter("clientLogin", login);
        return q.getResultList();
    }

    @Override
    public Purchase getById(Long id) {
        TypedQuery<Purchase> q = em.createQuery("SELECT p FROM Purchase p WHERE p.id = :id", Purchase.class);
        q.setParameter("id", id);
        return q.getSingleResult();
    }

    @Transactional
    @Override
    public Purchase update(Purchase purchase) {
        em.merge(purchase);
        return purchase;
    }
}
