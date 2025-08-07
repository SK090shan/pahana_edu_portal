package com.pahana.util;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDbConnection {

    public static void main(String[] args) {
        System.out.println("Attempting to connect to the database...");
        
        Connection connection = null;
        try {
            // Get an instance of our Singleton DatabaseManager and try to get a connection
            connection = DatabaseManager.getInstance().getConnection();
            
            // If the connection is not null and is valid, we succeeded!
            if (connection != null && !connection.isClosed()) {
                System.out.println("-------------------------------------------");
                System.out.println("DATABASE CONNECTION SUCCESSFUL!");
                System.out.println("-------------------------------------------");
            } else {
                System.err.println("-------------------------------------------");
                System.err.println("FAILED TO CONNECT. Connection object is null or closed.");
                System.err.println("-------------------------------------------");
            }
            
        } catch (SQLException e) {
            // This is the most important part! It will print the REAL error.
            System.err.println("-------------------------------------------");
            System.err.println("SQL EXCEPTION CAUGHT! CONNECTION FAILED.");
            System.err.println("Here is the real error message:");
            e.printStackTrace(); // This prints the full error stack trace.
            System.err.println("-------------------------------------------");
            
        } finally {
            // Always try to close the connection
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}