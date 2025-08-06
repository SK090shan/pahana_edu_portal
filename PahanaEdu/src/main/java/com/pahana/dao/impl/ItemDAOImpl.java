package com.pahana.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.util.List;
import com.pahana.dao.ItemDAO;
import com.pahana.model.item.Item;
import com.pahana.util.DatabaseManager; 

public class ItemDAOImpl implements ItemDAO {

    @Override
    public boolean addItem(Item item) {
        // Implementation for addItem
        return false;
    }

    @Override
    public Item getItemById(int itemId) {
        // Implementation for getItemById
        return null;
    }

    @Override
    public List<Item> getAllItems() {
        // Implementation for getAllItems
        return new ArrayList<>(); // Return empty list to avoid errors for now
    }

    @Override
    public boolean updateItem(Item item) {
        // Implementation for updateItem
        return false;
    }

    @Override
    public boolean deleteItem(int itemId) {
        // Implementation for deleteItem
        return false;
    }

    @Override
    public int getTotalItemCount() {
        String sql = "SELECT COUNT(*) FROM Items";
        // The try-with-resources statement requires Connection, PreparedStatement, and ResultSet to be imported
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) { // SQLException also needs to be imported
            e.printStackTrace();
        }
        return 0;
    }
    
    // You would also have a private helper method like this
    private Item mapResultSetToItem(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setItemId(rs.getInt("itemId"));
        item.setItemName(rs.getString("itemName"));
        // Assuming your Item model has setAuthor, etc.
        // item.setAuthor(rs.getString("author")); 
        item.setItemPrice(rs.getBigDecimal("price"));
        item.setStockQuantity(rs.getInt("stock"));
        return item;
    }
}