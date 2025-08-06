package com.pahana.dao;



import java.util.List;
import com.pahana.model.user.User;

/**
 * LECTURE 2: OOP - Abstraction.
 * This interface defines the contract for data access operations related to the User entity.
 * Any class that handles user data persistence must implement these methods. This decouples
 * the business logic (controllers) from the specific database implementation.
 */
public interface UserDAO {

    /**
     * Registers a new user in the database.
     * The password should already be hashed before being passed in the User object.
     * @param user The User object containing all necessary details.
     * @return true if the user was successfully registered, false otherwise (e.g., due to duplicate username/email).
     */
    boolean registerUser(User user);

    /**
     * Validates a user's credentials for logging in.
     * It checks the username, compares the hashed password, and ensures the user's status is 'ACTIVE'.
     * @param username The user's username.
     * @param plainPassword The plain-text password entered by the user.
     * @return A populated User object if authentication is successful, otherwise null.
     */
    User validateUser(String username, String plainPassword);
    
    /**
     * Retrieves a single user from the database based on their username.
     * @param username The username of the user to retrieve.
     * @return A User object if found, otherwise null.
     */
    User getUserByUsername(String username);
    
    /**
     * Retrieves a single user from the database based on their unique ID.
     * @param userId The unique ID of the user.
     * @return A User object if found, otherwise null.
     */
    User getUserById(int userId);

    /**
     * Retrieves a list of all users in the system. Intended for admin use.
     * @return A List of all User objects.
     */
    List<User> getAllUsers();

    /**
     * Updates the status of a specific user (e.g., from 'PENDING' to 'ACTIVE'). Intended for admin use.
     * @param userId The ID of the user to update.
     * @param newStatus The new status to set ('ACTIVE', 'INACTIVE').
     * @return true if the update was successful, false otherwise.
     */
    boolean updateUserStatus(int userId, String newStatus);
}