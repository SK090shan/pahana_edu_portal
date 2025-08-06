package com.pahana.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.pahana.dao.UserDAO;
import com.pahana.model.user.User;
import com.pahana.util.DatabaseManager;

/**
 * Implements the UserDAO interface, providing concrete database operations for User entities.
 * This class uses JDBC and PreparedStatement to interact securely with the SQL Server database.
 * It relies on the Singleton DatabaseManager to obtain database connections.
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public boolean registerUser(User user) {
        String sql = "INSERT INTO Users (fullName, username, email, password_hash, role) VALUES (?, ?, ?, ?, ?)";
        
        // Using try-with-resources to ensure the connection and statement are always closed.
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPasswordHash());
            stmt.setString(5, user.getRole());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("SQL Error during user registration: " + e.getMessage());
            // This error often occurs if username or email is not unique, which is a valid business case.
            return false;
        }
    }

    @Override
    public User validateUser(String username, String plainPassword) {
        User user = getUserByUsername(username);
        
        // The user must exist, the password must match, and their account must be 'ACTIVE'.
        if (user != null && "ACTIVE".equals(user.getStatus())) {
            if (BCrypt.checkpw(plainPassword, user.getPasswordHash())) {
                return user; // Successful login
            }
        }
        
        return null; // Login failed
    }

    @Override
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE userId = ?";
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY fullName";
        
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                userList.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public boolean updateUserStatus(int userId, String newStatus) {
        String sql = "UPDATE Users SET status = ? WHERE userId = ?";
        
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * A private helper method to avoid code duplication.
     * It maps a row from a ResultSet to a User object.
     * @param rs The ResultSet to map from.
     * @return A populated User object.
     * @throws SQLException
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("userId"));
        user.setFullName(rs.getString("fullName"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setRole(rs.getString("role"));
        user.setStatus(rs.getString("status"));
        return user;
    }
}