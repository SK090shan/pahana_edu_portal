package com.pahana.dao;


import com.pahana.model.bill.Bill;
import java.util.List;



public interface BillDAO {
    // Saves a bill and its items in a single transaction
    int saveBill(Bill bill); 
    Bill getBillById(int billId);
    List<Bill> getAllBills(); 
}