package com.pahana.model.bill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Bill {
    private int billId;
    private int customerId;
    private int staffId;
    private Date billDate;
    private BigDecimal totalAmount;
    private List<BillItem> billItems; 

    // Getters and Setters for all fields...
    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public int getStaffId() { return staffId; }
    public void setStaffId(int staffId) { this.staffId = staffId; }
    public Date getBillDate() { return billDate; }
    public void setBillDate(Date billDate) { this.billDate = billDate; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public List<BillItem> getBillItems() { return billItems; }
    public void setBillItems(List<BillItem> billItems) { this.billItems = billItems; }
}