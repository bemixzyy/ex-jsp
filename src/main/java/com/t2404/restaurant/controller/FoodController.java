package com.t2404.restaurant.controller;

import com.t2404.restaurant.entity.Food;
import com.t2404.restaurant.repository.FoodRepository;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/foods")
public class FoodController extends HttpServlet {
    private final FoodRepository repo = new FoodRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Food> foods = repo.findAllSelling();
        req.setAttribute("foods", foods);
        req.getRequestDispatcher("foods.jsp").forward(req, resp);
    }
}
