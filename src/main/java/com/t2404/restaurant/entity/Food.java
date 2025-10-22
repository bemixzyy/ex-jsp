package com.t2404.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    private int id;                     // ID tự tăng
    private String code;                // Mã món ăn (unique)
    private String name;                // Tên món ăn
    private String categoryCode;        // Mã danh mục (FK)
    private String description;         // Mô tả món ăn
    private String imagePath;           // Đường dẫn ảnh
    private BigDecimal price;           // Giá bán
    private LocalDate startDate;        // Ngày bắt đầu bán
    private LocalDateTime updateDate;   // Ngày cập nhật gần nhất
    private FoodStatus status;          // Trạng thái món ăn

    /**
     * Constructor dành cho khi thêm món mới.
     * - startDate = ngày hiện tại
     * - status mặc định là DANG_BAN
     */
    public Food(String code, String name, String categoryCode, String description,
                String imagePath, BigDecimal price) {
        this.code = code;
        this.name = name;
        this.categoryCode = categoryCode;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
        this.startDate = LocalDate.now();
        this.status = FoodStatus.DANG_BAN;
    }
}
