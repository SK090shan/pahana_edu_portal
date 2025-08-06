package com.pahana.dao.impl;

import com.pahana.dao.BillDAO;
import com.pahana.model.bill.Bill;
import com.pahana.model.bill.BillItem;
import com.pahana.util.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        Bill bill = null;
        String billSQL = "SELECT * FROM Bills WHERE billId = ?";
        // This SQL joins three tables to get all the data we need in one go
        String itemsSQL = "SELECT bi.*, i.itemName FROM BillItems bi JOIN Items i ON bi.itemId = i.itemId WHERE bi.billId = ?";
        
        try (Connection conn = DatabaseManager.getInstance().getConnection()) {
            // First, get the main bill details
            try (PreparedStatement stmt = conn.prepareStatement(billSQL)) {
                stmt.setInt(1, billId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    bill = new Bill();
                    bill.setBillId(rs.getInt("billId"));
                    bill.setCustomerId(rs.getInt("customerId"));
                    bill.setStaffId(rs.getInt("staffId"));
                    bill.setBillDate(rs.getTimestamp("billDate"));
                    bill.setTotalAmount(rs.getBigDecimal("totalAmount"));
                }
            }

            // If a bill was found, get its line items
            if (bill != null) {
                List<BillItem> billItems = new ArrayList<>();
                try (PreparedStatement stmt = conn.prepareStatement(itemsSQL)) {
                    stmt.setInt(1, billId);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        BillItem item = new BillItem();
                        item.setBillItemId(rs.getInt("billItemId"));
                        item.setBillId(rs.getInt("billId"));
                        item.setItemId(rs.getInt("itemId"));
                        item.setQuantity(rs.getInt("quantity"));
                        item.setPricePerUnit(rs.getBigDecimal("pricePerUnit"));
                        item.setItemName(rs.getString("itemName")); // Get the joined item name
                        billItems.add(item);
                    }
                }
                bill.setBillItems(billItems);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bill;
    }
}