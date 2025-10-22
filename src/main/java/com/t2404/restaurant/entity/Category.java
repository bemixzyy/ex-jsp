package com.t2404.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private String code;   // Mã danh mục (primary key)
    private String name;   // Tên danh mục
}
