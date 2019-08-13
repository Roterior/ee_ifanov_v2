package com.accenture.flowershop.be.access;

import com.accenture.flowershop.be.entity.Flower;
import com.accenture.flowershop.be.entity.FlowerFilter;
import java.util.List;

public interface FlowerDAO {
    
    Flower getById(Long id);

    List<Flower> getByFilter(FlowerFilter flowerFilter);

    Flower update(Flower flower);

}
