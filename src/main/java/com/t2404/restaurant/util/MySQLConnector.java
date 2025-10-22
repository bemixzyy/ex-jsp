package com.t2404.restaurant.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
    private static final String DATABASE_HOST = "jdbc:mysql://localhost:8889/";
    private static final String DATABASE_NAME = "restaurant";
    private static final String DATABASE_PARAMS = "?useSSL=false&serverTimezone=UTC";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = DATABASE_HOST + DATABASE_NAME + DATABASE_PARAMS;
            connection = DriverManager.getConnection(url, DATABASE_USERNAME, DATABASE_PASSWORD);
            System.out.println("Mở kết nối thành công đến MySQL");
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            System.err.println("Không thể kết nối đến với DB");
        }
        return connection;
    }
}
