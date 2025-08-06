package com.pahana.dao.impl;

import com.pahana.dao.BillDAO;
import com.pahana.model.bill.Bill;
import com.pahana.model.bill.BillItem;
import com.pahana.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;

public class BillDAOImpl implements BillDAO {

    @Override
    public int saveBill(Bill bill) {
        String insertBillSQL = "INSERT INTO Bills (customerId, staffId, totalAmount) OUTPUT INSERTED.billId VALUES (?, ?, ?)";
        String insertBillItemSQL = "INSERT INTO BillItems (billId, itemId, quantity, pricePerUnit) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        int generatedBillId = -1;

        try {
            conn = DatabaseManager.getInstance().getConnection();
            // LECTURE: Database Transactions. Set auto-commit to false to start a transaction.
            conn.setAutoCommit(false);

            // Insert the main bill record and get the generated ID
            try (PreparedStatement stmtBill = conn.prepareStatement(insertBillSQL)) {
                stmtBill.setInt(1, bill.getCustomerId());
                stmtBill.setInt(2, bill.getStaffId());
                stmtBill.setBigDecimal(3, bill.getTotalAmount());
                
                ResultSet rs = stmtBill.executeQuery();
                if (rs.next()) {
                    generatedBillId = rs.getInt(1);
                } else {
                    throw new SQLException("Creating bill failed, no ID obtained.");
                }
            }
            
            // Now, insert each line item associated with that bill ID
            try (PreparedStatement stmtItem = conn.prepareStatement(insertBillItemSQL)) {
                for (BillItem item : bill.getBillItems()) {
                    stmtItem.setInt(1, generatedBillId);
                    stmtItem.setInt(2, item.getItemId());
                    stmtItem.setInt(3, item.getQuantity());
                    stmtItem.setBigDecimal(4, item.getPricePerUnit());
                    stmtItem.addBatch(); // Add this query to a batch for efficiency
                }
                stmtItem.executeBatch(); // Execute all item insert queries at once
            }

            conn.commit(); // If everything succeeds, commit the transaction
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // If any part fails, roll back the entire transaction
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return -1; // Return -1 to indicate failure
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return generatedBillId; // Return the new bill's ID on success
    }

    @Override
    public Bill getBillById(int billId) {
        // You would implement this to fetch a bill for viewing/reprinting.
        // It would involve joining Bills, BillItems, and Items tables.
        return null; 
    }
}