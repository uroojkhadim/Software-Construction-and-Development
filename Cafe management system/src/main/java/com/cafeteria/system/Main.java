package com.cafeteria.system;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Cafeteria Management System ---");

        // Create Admin
        Admin admin = new Admin("A001", "Alice", "admin123");
        admin.login();

        // Create MenuItem
        MenuItem coffee = new MenuItem(1, "Coffee", 2.50, "Beverage", "coffee.jpg");
        coffee.addItem();

        // Create Cashier
        Cashier cashier = new Cashier("C001", "Bob", "bob456");
        cashier.login();

        // Create Order
        Order order = new Order("ORD001", 5);
        order.addMenuItem(coffee);
        System.out.println("Order Total: " + order.getGrandTotal());

        // Create Payment
        Payment payment = new Payment("P001", order.getGrandTotal(), PaymentMethod.CARD, order);
        payment.createInvoice();
        payment.recordTransaction();

        // Create Table
        Table table = new Table(5);
        table.assignTable(order);
        System.out.println("Table " + table.getId() + " Status: " + table.getStatus().getLabel());

        // Create Customer
        Customer customer = new Customer("CUST001", "Charlie", "123-456-7890");
        customer.addLoyaltyPoints(10);

        // Generate Report
        SalesReport salesReport = new SalesReport(new ArrayList<>());
        salesReport.generateDailyReport();
        salesReport.exportReport("PDF");

        // Logout
        cashier.logout();
        admin.logout();
    }
}
