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
        String insertBillSQL = "INSERT INTO Bills (customerId, staffId, totalAmount) VALUES (?, ?, ?)";
        String insertBillItemSQL = "INSERT INTO BillItems (billId, itemId, quantity, pricePerUnit) VALUES (?, ?, ?, ?)";
        
        Connection conn = null;
        int generatedBillId = -1;

        try {
            conn = DatabaseManager.getInstance().getConnection();
            conn.setAutoCommit(false); // Start transaction

            try (PreparedStatement stmtBill = conn.prepareStatement(insertBillSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmtBill.setInt(1, bill.getCustomerId());
                stmtBill.setInt(2, bill.getStaffId());
                stmtBill.setBigDecimal(3, bill.getTotalAmount());
                
                if (stmtBill.executeUpdate() == 0) throw new SQLException("Creating bill failed, no rows affected.");

                try (ResultSet generatedKeys = stmtBill.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedBillId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating bill failed, no ID obtained.");
                    }
                }
            }

            try (PreparedStatement stmtItem = conn.prepareStatement(insertBillItemSQL)) {
                for (BillItem item : bill.getBillItems()) {
                    stmtItem.setInt(1, generatedBillId);
                    stmtItem.setInt(2, item.getItemId());
                    stmtItem.setInt(3, item.getQuantity());
                    stmtItem.setBigDecimal(4, item.getPricePerUnit());
                    stmtItem.addBatch();
                }
                stmtItem.executeBatch();
            }

            conn.commit();

        } catch (SQLException e) {
            System.err.println("SQL Exception in saveBill! Rolling back.");
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return -1;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return generatedBillId;
    }

    @Override
    public Bill getBillById(int billId) {
        Bill bill = null;
        String billSQL = "SELECT * FROM Bills WHERE billId = ?";
        String itemsSQL = "SELECT bi.*, i.itemName FROM BillItems bi JOIN Items i ON bi.itemId = i.itemId WHERE bi.billId = ?";
        
        try (Connection conn = DatabaseManager.getInstance().getConnection()) {
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
                } else {
                    return null;
                }
            }

            List<BillItem> billItems = new ArrayList<>();
            try (PreparedStatement stmt = conn.prepareStatement(itemsSQL)) {
                stmt.setInt(1, billId);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    BillItem item = new BillItem();
                    item.setBillItemId(rs.getInt("billItemId"));
                    item.setItemId(rs.getInt("itemId"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPricePerUnit(rs.getBigDecimal("pricePerUnit"));
                    item.setItemName(rs.getString("itemName"));
                    billItems.add(item);
                }
            }
            bill.setBillItems(billItems);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bill;
    }
}