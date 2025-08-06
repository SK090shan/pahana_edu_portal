package com.pahana.service;

import com.pahana.model.customer.Customer;
import com.pahana.model.item.Item;
import com.pahana.model.user.User;

import java.util.List;

/**
 * LECTURE: Service Layer and Reusable Business Logic.
 * This service encapsulates the logic for converting lists of model objects
 * into a CSV (Comma-Separated Values) formatted string for download.
 */
public class CsvExportService {

    // Helper method to safely handle commas within data
    private String escapeCsvField(String data) {
        if (data == null) return "";
        if (data.contains(",")) {
            return "\"" + data.replace("\"", "\"\"") + "\"";
        }
        return data;
    }

    public String exportUsers(List<User> users) {
        StringBuilder csv = new StringBuilder();
        // Header Row
        csv.append("User ID,Full Name,Username,Email,Role,Status\n");
        // Data Rows
        for (User user : users) {
            csv.append(user.getUserId()).append(",");
            csv.append(escapeCsvField(user.getFullName())).append(",");
            csv.append(escapeCsvField(user.getUsername())).append(",");
            csv.append(escapeCsvField(user.getEmail())).append(",");
            csv.append(escapeCsvField(user.getRole())).append(",");
            csv.append(escapeCsvField(user.getStatus())).append("\n");
        }
        return csv.toString();
    }

    public String exportCustomers(List<Customer> customers) {
        StringBuilder csv = new StringBuilder();
        // Header Row
        csv.append("Customer ID,Account Number,Full Name,Address,Telephone\n");
        // Data Rows
        for (Customer c : customers) {
            csv.append(c.getCustomerId()).append(",");
            csv.append(escapeCsvField(c.getAccountNumber())).append(",");
            csv.append(escapeCsvField(c.getFullName())).append(",");
            csv.append(escapeCsvField(c.getAddress())).append(",");
            csv.append(escapeCsvField(c.getTelephone())).append("\n");
        }
        return csv.toString();
    }

    public String exportItems(List<Item> items) {
        StringBuilder csv = new StringBuilder();
        // Header Row
        csv.append("Item ID,Item Name,Price,Stock\n");
        // Data Rows
        for (Item item : items) {
            csv.append(item.getItemId()).append(",");
            csv.append(escapeCsvField(item.getItemName())).append(",");
            csv.append(item.getPrice()).append(",");
            csv.append(item.getStock()).append("\n");
        }
        return csv.toString();
    }
}