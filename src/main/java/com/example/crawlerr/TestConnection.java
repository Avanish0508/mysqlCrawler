package com.example.crawlerr;

import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/sampledb";
        String username = "root";
        String password = "smellypp69";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("✅ Connection successful!");
        } catch (SQLException e) {
            System.out.println("❌ Connection failed");
            e.printStackTrace();
        }
    }
}
