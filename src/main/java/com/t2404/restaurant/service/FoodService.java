package com.t2404.restaurant.service;

import com.t2404.restaurant.entity.Food;
import com.t2404.restaurant.repository.FoodRepository;

public class FoodService {
    private final FoodRepository repo = new FoodRepository();

    public boolean addFood(Food food) {
        // Validate đơn giản
        if (food.getName() == null || food.getName().isEmpty()) return false;
        if (food.getPrice() == null || food.getPrice().doubleValue() <= 0) return false;
        if (food.getCategoryCode() == null || food.getCategoryCode().isEmpty()) return false;

        return repo.insert(food);
    }

    public java.util.List<com.t2404.restaurant.entity.Food> getSellingFoods() {
        return repo.findAllSelling();
    }
}
