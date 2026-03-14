package com.cafeteria.system;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    // Refactoring: Rename Field - Changed 'customerID' to 'id' for project-wide consistency
    private String id;
    private String name;
    private String contact;
    private int loyaltyPoints;
    private List<Order> orderHistory;

    public Customer(String id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.loyaltyPoints = 0;
        this.orderHistory = new ArrayList<>();
    }

    public void addOrder(Order order) {
        this.orderHistory.add(order);
        // Automatically add loyalty points based on order total (e.g., 1 point per $10)
        addLoyaltyPoints((int) (order.getGrandTotal() / 10));
    }

    public List<Order> getOrderHistory() {
        return new ArrayList<>(orderHistory);
    }

    public void addLoyaltyPoints(int points) {
        // Code Smell: Data Integrity - Added Guard Clause to prevent negative points
        if (points < 0) {
            throw new IllegalArgumentException("Loyalty points cannot be negative");
        }
        this.loyaltyPoints += points;
        System.out.println("New Loyalty Points for " + name + ": " + loyaltyPoints);
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(int loyaltyPoints) {
        if (loyaltyPoints < 0) {
            throw new IllegalArgumentException("Loyalty points cannot be negative");
        }
        this.loyaltyPoints = loyaltyPoints;
    }
}
