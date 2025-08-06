package com.pahana.dao;

import java.util.List;
import com.pahana.model.item.Item;

public interface ItemDAO {
	 boolean addItem(Item item);
	    Item getItemById(int itemId);
	    List<Item> getAllItems();
	    boolean updateItem(Item item);
	    boolean deleteItem(int itemId);
	    int getTotalItemCount();
}
