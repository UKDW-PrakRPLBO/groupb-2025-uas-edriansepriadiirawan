package org.uas.repository;

import org.uas.data.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
        createTable();
    }

    public void createTable() {
        // Create database tables if they don't exist
        // Implement this method to create tables for users, courses, classes, and attendance records
        String userTableSql = "CREATE TABLE IF NOT EXISTS users ("
                + "email TEXT NOT NULL PRIMARY KEY,"
                + "username TEXT NOT NULL UNIQUE,"
                + "password TEXT NOT NULL"
                + ")";
        if (connection != null) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(userTableSql);
                // Execute more table creation statements as needed
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                // Handle table creation error
            }
        }
    }

    public List<User> findAll() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (PreparedStatement pesan = connection.prepareStatement(query)) {
            ResultSet rs = pesan.executeQuery();
            while (rs.next()) {
                String email = rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");
                users.add(new User(email, username, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pesan = connection.prepareStatement(query)) {
            pesan.setString(1, username);
            pesan.setString(2, password);
            ResultSet rs = pesan.executeQuery();
            return (rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean insertUser(String email, String username, String password) {
        String query = "INSERT INTO users (email, username, password) VALUES (?, ?, ?)";
        try (PreparedStatement pesan = connection.prepareStatement(query)) {
            pesan.setString(1, email);
            pesan.setString(2, username);
            pesan.setString(3, password);
            int rowsAffected = pesan.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(String email, String username, String password) {
        String query = "UPDATE users SET username = ?, password = ? WHERE email = ?";
        try (PreparedStatement pesan = connection.prepareStatement(query)) {
            pesan.setString(1, username);
            pesan.setString(2, password);
            pesan.setString(3, email);
            int rowsAffected = pesan.executeUpdate();
            if ( rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String email) {
    String query = "DELETE FROM users WHERE email = ?";
    try (PreparedStatement pesan = connection.prepareStatement(query)) {
        pesan.setString(1, email);
        int rowsAffected = pesan.executeUpdate();
        if (rowsAffected > 0) {
            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return false;
    }
}

