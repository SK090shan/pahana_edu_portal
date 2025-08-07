package com.pahana.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**Implementing the Singleton Design Pattern.
 * This class ensures that only one instance of the DatabaseManager exists, 
 * providing a global point of access to the database connection. The constructor is private, 
 * and the getInstance() method controls the instantiation.
 * The 'synchronized' keyword ensures thread safety in a multi-user web environment.
 */
public class DatabaseManager {

    private static DatabaseManager instance;


    private static final String URL = "jdbc:sqlserver://DESKTOP\\SQLEXPRESS;databaseName=pahana_edu_db;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345678";
    private static final String DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    // Private constructor to prevent direct instantiation
    private DatabaseManager() {
        try {
            // Load the database driver
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Database driver not found.");
            e.printStackTrace();
        }
    }

    // Public method to provide access to the instance
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    // Method to get a new database connection
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}