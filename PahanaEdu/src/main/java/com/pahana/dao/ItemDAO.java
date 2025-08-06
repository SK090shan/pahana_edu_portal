package com.pahana.dao;

import com.pahana.model.item.Item;
import java.util.List;

/**
 * DAO Interface for Item operations. Defines the contract for any class
 * that will handle item data persistence.
 */
public interface ItemDAO {
    boolean addItem(Item item);
    Item getItemById(int itemId);
    List<Item> getAllItems();
    boolean updateItem(Item item);
    boolean deleteItem(int itemId);
    int getTotalItemCount(); // For the dashboard
}