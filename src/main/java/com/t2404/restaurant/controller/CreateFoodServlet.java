package com.t2404.restaurant.controller;

import com.t2404.restaurant.entity.Food;
import com.t2404.restaurant.entity.FoodStatus;
import com.t2404.restaurant.repository.FoodRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

public class CreateFoodServlet extends HttpServlet {

    private FoodRepository foodRepository = new FoodRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Hiển thị form thêm món ăn
        req.getRequestDispatcher("/WEB-INF/food-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String categoryCode = req.getParameter("categoryCode");
        String imagePath = req.getParameter("imagePath");
        String priceStr = req.getParameter("price");

        // ========== VALIDATE ==========
        String error = null;
        if (name == null || name.trim().length() < 7) {
            error = "Tên món ăn phải có ít nhất 7 ký tự.";
        } else if (priceStr == null || priceStr.isEmpty() || Double.parseDouble(priceStr) <= 0) {
            error = "Giá bán phải lớn hơn 0.";
        }

        if (error != null) {
            req.setAttribute("error", error);
            req.getRequestDispatcher("/WEB-INF/food-form.jsp").forward(req, resp);
            return;
        }

        BigDecimal price = new BigDecimal(priceStr);

        // Sinh mã món ăn ngẫu nhiên
        String code = "F" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        Food food = new Food(code, name, categoryCode, description, imagePath, price);
        food.setStatus(FoodStatus.DANG_BAN);

        boolean isSaved = foodRepository.save(food);

        if (isSaved) {
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("error", "Không thể thêm món ăn vào cơ sở dữ liệu!");
            req.getRequestDispatcher("/WEB-INF/food-form.jsp").forward(req, resp);
        }
    }
}
