package com.cafeteria.system;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Order {
    // Refactoring: Rename Field - Changed 'orderID' to 'id'
    private String id;
    private Date date;
    // Refactoring: Replace Type Code with Enum - Changed 'status' to Enum to avoid String-based magic values
    private Status status;
    private int tableNumber;
    private double tax;
    private double discount;
    private double grandTotal;
    private List<MenuItem> menuItems;

    public Order(String id, int tableNumber) {
        this.id = id;
        this.date = new Date();
        this.status = Status.PENDING;
        this.tableNumber = tableNumber;
        this.menuItems = new ArrayList<>();
    }

    public void addMenuItem(MenuItem item) {
        this.menuItems.add(item);
        calculateTotal();
    }

    public void calculateTotal() {
        double subtotal = 0;
        for (MenuItem item : menuItems) {
            subtotal += item.getPrice();
        }
        // Formula: (Subtotal + Tax) - Discount
        this.grandTotal = (subtotal + tax) - discount;
        if (this.grandTotal < 0) this.grandTotal = 0;
    }

    public double getSubtotal() {
        double subtotal = 0;
        for (MenuItem item : menuItems) {
            subtotal += item.getPrice();
        }
        return subtotal;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public int getTableNumber() { return tableNumber; }
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }
    public double getTax() { return tax; }
    public void setTax(double tax) {
        if (tax < 0) {
            throw new IllegalArgumentException("Tax cannot be negative");
        }
        this.tax = tax;
    }
    public double getDiscount() { return discount; }
    public void setDiscount(double discount) {
        if (discount < 0) {
            throw new IllegalArgumentException("Discount cannot be negative");
        }
        this.discount = discount;
    }
    public double getGrandTotal() { return grandTotal; }
    public List<MenuItem> getMenuItems() { return new ArrayList<>(menuItems); }
}
