package com.accenture.flowershop.be.business;

import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.FlowerFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface FlowerService {

    Flower getById(Long id);

    List<Flower> getByFilter(FlowerFilter flowerFilter);

    Flower updateQuantity(Long id, Integer quantity);

    void onSearchButton(HttpServletRequest req, HttpServletResponse resp);

    void onAddButton(HttpServletRequest req, HttpServletResponse resp);

    void onRemoveButton(HttpServletRequest req, HttpServletResponse resp);

}
