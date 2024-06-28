package com.example.practice.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class H2CacheManager {
    private static final String DB_URL = "jdbc:h2:E:/AAA/Practice/src/main/resources/com/example/practice/db/cacheDB";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public H2CacheManager() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS cache (\"key\" VARCHAR(255) PRIMARY KEY, \"value\" VARCHAR(255))";
            try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setCache(String key, String value) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String insertOrUpdateSQL = "MERGE INTO cache (\"key\", \"value\") VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertOrUpdateSQL)) {
                statement.setString(1, key);
                statement.setString(2, value);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getCache(String key) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String selectSQL = "SELECT \"value\" FROM cache WHERE \"key\" = ?";
            try (PreparedStatement statement = connection.prepareStatement(selectSQL)) {
                statement.setString(1, key);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("value");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        H2CacheManager cacheManager = new H2CacheManager();
        cacheManager.setCache("testKey", "testValue");
        String value = cacheManager.getCache("testKey");
        System.out.println("Cached value: " + value);
    }
}
