package com.t2404.restaurant.service;

import com.t2404.restaurant.entity.Food;
import com.t2404.restaurant.repository.FoodRepository;

public class FoodService {
    private FoodRepository repo = new FoodRepository();

    public boolean addFood(Food food) {
        if (food.getName() == null || food.getName().length() < 7) return false;
        if (food.getPrice().doubleValue() <= 0) return false;
        return repo.insert(food);
    }
}
