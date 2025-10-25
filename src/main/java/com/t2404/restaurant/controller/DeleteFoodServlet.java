package com.t2404.restaurant.controller;

import com.t2404.restaurant.entity.Food;
import com.t2404.restaurant.entity.FoodStatus;
import com.t2404.restaurant.repository.FoodRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/food/delete")
public class DeleteFoodServlet extends HttpServlet {

    private final FoodRepository foodRepository = new FoodRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy id từ query parameter (VD: /food/delete?id=5)
        String idParam = req.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID món ăn cần xoá");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            Food food = foodRepository.findById(id);

            if (food == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy món ăn có ID = " + id);
                return;
            }

            // Cập nhật trạng thái món ăn thay vì xoá hẳn
            food.setStatus(FoodStatus.DA_XOA);
            food.setUpdateDate(LocalDateTime.now());
            foodRepository.update(food);

            // Sau khi xoá thành công -> chuyển hướng về trang danh sách
            resp.sendRedirect(req.getContextPath() + "/");

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID không hợp lệ");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi xoá món ăn");
        }
    }
}
