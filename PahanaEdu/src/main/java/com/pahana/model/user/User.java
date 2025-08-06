package com.pahana.model.user;

/**
 * LECTURE 1 & 2: Represents the data structure for a User.
 * This class uses private fields and public getters/setters, demonstrating
 * the core OOP principle of Encapsulation.
 */

public class User {
    private int userId;
    private String fullName;
    private String username;
    private String email;
    private String passwordHash; // We only store the hash, never the plain password
    private String role;
    private String status;

    // --- Getters and Setters ---
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}