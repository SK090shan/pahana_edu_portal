package com.pahana.model.item;

import java.math.BigDecimal;

/**
 * Model (POJO) representing a single Item in the bookstore inventory.
 * Uses BigDecimal for price to ensure financial accuracy.
 */
public class Item {
    private int itemId;
    private String itemName;
    private BigDecimal price;
    private int stock;

    // --- Getters and Setters ---
    public int getItemId() {
        return itemId;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
}