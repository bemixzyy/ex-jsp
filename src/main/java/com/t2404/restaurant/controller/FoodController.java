package com.t2404.restaurant.controller;

import com.t2404.restaurant.entity.Food;
import com.t2404.restaurant.entity.FoodStatus;
import com.t2404.restaurant.repository.FoodRepository;
import com.t2404.restaurant.service.FoodService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@WebServlet("/foods")
public class FoodController extends HttpServlet {
    private final FoodRepository repo = new FoodRepository();
    private final FoodService service = new FoodService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Food> foods = repo.findAllSelling();
        req.setAttribute("foods", foods);
        req.getRequestDispatcher("foods.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String categoryCode = req.getParameter("category_code");
        String description = req.getParameter("description");
        String imagePath = req.getParameter("image_path");
        BigDecimal price = new BigDecimal(req.getParameter("price"));
        String code = "F" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        Food food = new Food(code, name, categoryCode, description, imagePath, price);
        food.setStatus(FoodStatus.DANG_BAN);

        boolean isSaved = service.addFood(food);
        if (isSaved) {
            resp.sendRedirect("foods");
        } else {
            req.setAttribute("error", "Không thể thêm món ăn. Vui lòng kiểm tra lại.");
            req.getRequestDispatcher("/WEB-INF/food-form.jsp").forward(req, resp);
        }
    }
}
