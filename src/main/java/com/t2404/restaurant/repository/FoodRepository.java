package com.t2404.restaurant.repository;

import com.t2404.restaurant.entity.Food;
import com.t2404.restaurant.entity.FoodStatus;
import com.t2404.restaurant.util.MySQLConnector;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FoodRepository {

    public boolean save(Food food) {
        String sql = "INSERT INTO foods (code, name, category_code, description, image_path, price, start_date, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Gán giá trị vào câu lệnh SQL
            stmt.setString(1, food.getCode());
            stmt.setString(2, food.getName());
            stmt.setString(3, food.getCategoryCode());
            stmt.setString(4, food.getDescription());
            stmt.setString(5, food.getImagePath());
            stmt.setBigDecimal(6, food.getPrice());

            // Nếu startDate bị null thì mặc định là ngày hôm nay
            LocalDate start = food.getStartDate() != null ? food.getStartDate() : LocalDate.now();
            stmt.setDate(7, Date.valueOf(start));

            // Enum lưu dưới dạng chuỗi (ví dụ: DANG_BAN)
            stmt.setString(8, food.getStatus().name());

            int rows = stmt.executeUpdate();
            System.out.println("✅ Thêm món ăn thành công (" + rows + " dòng)");
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thêm món ăn: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Food> findAllSelling() {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT * FROM foods WHERE status = 'DANG_BAN'";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Food food = new Food();
                food.setId(rs.getInt("id"));
                food.setCode(rs.getString("code"));
                food.setName(rs.getString("name"));
                food.setCategoryCode(rs.getString("category_code"));
                food.setDescription(rs.getString("description"));
                food.setImagePath(rs.getString("image_path"));
                food.setPrice(rs.getBigDecimal("price"));
                food.setStartDate(rs.getDate("start_date").toLocalDate());
                Timestamp updateTime = rs.getTimestamp("update_date");
                food.setUpdateDate(updateTime != null ? updateTime.toLocalDateTime() : null);
                food.setStatus(FoodStatus.valueOf(rs.getString("status")));
                list.add(food);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean insert(Food food) {
        String sql = "INSERT INTO foods (code, name, category_code, description, image_path, price, start_date, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, food.getCode());
            ps.setString(2, food.getName());
            ps.setString(3, food.getCategoryCode());
            ps.setString(4, food.getDescription());
            ps.setString(5, food.getImagePath());
            ps.setBigDecimal(6, food.getPrice());
            ps.setDate(7, Date.valueOf(food.getStartDate()));
            ps.setString(8, food.getStatus().name());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Food food) {
        String sql = "UPDATE foods SET name=?, category_code=?, description=?, image_path=?, price=?, update_date=?, status=? WHERE id=?";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, food.getName());
            ps.setString(2, food.getCategoryCode());
            ps.setString(3, food.getDescription());
            ps.setString(4, food.getImagePath());
            ps.setBigDecimal(5, food.getPrice());
            ps.setTimestamp(6, Timestamp.valueOf(food.getUpdateDate()));
            ps.setString(7, food.getStatus().name());
            ps.setInt(8, food.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeStatus(int id, FoodStatus newStatus) {
        String sql = "UPDATE foods SET status = ?, update_date = NOW() WHERE id = ?";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus.name());
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Food findById(int id) {
        String sql = "SELECT * FROM foods WHERE id = ?";

        try (Connection conn = MySQLConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToFood(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Food mapResultSetToFood(ResultSet rs) throws SQLException {
        Food food = new Food();
        food.setId(rs.getInt("id"));
        food.setCode(rs.getString("code"));
        food.setName(rs.getString("name"));
        food.setCategoryCode(rs.getString("category_code"));
        food.setDescription(rs.getString("description"));
        food.setImagePath(rs.getString("image_path"));
        food.setPrice(rs.getBigDecimal("price"));

        Date start = rs.getDate("start_date");
        if (start != null) {
            food.setStartDate(start.toLocalDate());
        }

        Timestamp updated = rs.getTimestamp("update_date");
        if (updated != null) {
            food.setUpdateDate(updated.toLocalDateTime());
        }

        food.setStatus(FoodStatus.valueOf(rs.getString("status")));

        return food;
    }
}
