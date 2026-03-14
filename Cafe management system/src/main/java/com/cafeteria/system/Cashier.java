package com.cafeteria.system;

public class Cashier extends User {

    public Cashier(String id, String name, String password) {
        super(id, name, UserRole.CASHIER, password);
    }

    public void createOrder() {
        System.out.println("Creating order...");
    }

    public void modifyOrder(String orderId) {
        System.out.println("Modifying order with ID: " + orderId);
    }

    public void cancelOrder(String orderId) {
        System.out.println("Cancelling order with ID: " + orderId);
    }

    public void manageUser() {
        System.out.println("Managing user activities...");
    }

    @Override
    public void login() {
        System.out.println("Cashier " + getName() + " logged in.");
    }

    @Override
    public void logout() {
        System.out.println("Cashier " + getName() + " logged out.");
    }
}
