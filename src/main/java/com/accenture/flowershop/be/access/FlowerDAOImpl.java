package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.FlowerFilter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FlowerDAOImpl implements FlowerDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Flower getById(Long id) {
        TypedQuery<Flower> q = em.createQuery("SELECT f FROM Flower f WHERE f.id = :id", Flower.class);
        q.setParameter("id", id);
        return q.getSingleResult();
    }

    @Override
    public List<Flower> getByFilter(FlowerFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Flower> cq = cb.createQuery(Flower.class);
        Root<Flower> flower = cq.from(Flower.class);

        List<Predicate> predicates = new ArrayList<>();
        if (filter.getName() != null) {
            predicates.add(cb.like(cb.upper(flower.get("name")), "%" + filter.getName().toUpperCase() + "%"));
        }
        if (filter.getFromPrice() != null) {
            predicates.add(cb.ge(flower.get("price"), filter.getFromPrice()));
        }
        if (filter.getToPrice() != null) {
            predicates.add(cb.le(flower.get("price"), filter.getToPrice()));
        }

        cq.select(flower).where(predicates.toArray(new Predicate[]{})).orderBy(cb.asc(flower.get("name")));
        return em.createQuery(cq).getResultList();
    }

    @Transactional
    @Override
    public Flower update(Flower flower) {
        em.merge(flower);
        return flower;
    }
}
