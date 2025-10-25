package com.t2404.restaurant.controller;

import com.t2404.restaurant.entity.Category;
import com.t2404.restaurant.repository.CategoryRepository;
import com.t2404.restaurant.repository.FoodRepository;
import com.t2404.restaurant.entity.Food;
import com.t2404.restaurant.entity.FoodStatus;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class AddFoodServlet extends HttpServlet {

    private final FoodRepository foodRepository = new FoodRepository();
    private final CategoryRepository categoryRepository = new CategoryRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ✅ Lấy danh sách category từ DB
        List<Category> categories = categoryRepository.findAll();

        // ✅ Gửi dữ liệu qua JSP
        req.setAttribute("categories", categories);

        // ✅ Forward đến form thêm món
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/form-food.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String categoryCode = req.getParameter("categoryCode");
        String description = req.getParameter("description");
        String imagePath = req.getParameter("imagePath");
        BigDecimal price = new BigDecimal(req.getParameter("price"));

        Food food = new Food(code, name, categoryCode, description, imagePath, price);
        food.setStatus(FoodStatus.DANG_BAN);

        foodRepository.save(food);

        resp.sendRedirect(req.getContextPath() + "/foods");
    }
}
