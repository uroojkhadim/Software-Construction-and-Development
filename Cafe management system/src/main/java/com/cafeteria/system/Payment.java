package com.cafeteria.system;

import java.util.Date;

public class Payment {
    // Refactoring: Rename Field - Changed 'paymentID' to 'id'
    private String id;
    private double amount;
    // Refactoring: Replace Type Code with Enum - Changed from String to PaymentMethod
    private PaymentMethod paymentMethod;
    private Date date;
    private Order order;

    public Payment(String id, double amount, PaymentMethod paymentMethod, Order order) {
        // Code Smell: Lack of Validation - Added Guard Clause for negative payment amounts
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.id = id;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.date = new Date();
        this.order = order;
    }

    public void createInvoice() {
        System.out.println("Generating invoice for Payment ID: " + id + " using " + paymentMethod.getLabel());
    }

    public void recordTransaction() {
        System.out.println("Recording transaction for Amount: " + amount);
    }

    public void generateReports() {
        System.out.println("Generating report for the transaction.");
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = amount;
    }
    public String getPaymentMethodLabel() { return paymentMethod.getLabel(); }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}
