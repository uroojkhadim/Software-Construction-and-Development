package com.cafeteria.system;

public class Staff extends User {

    public Staff(String id, String name, String password) {
        super(id, name, UserRole.STAFF, password);
    }

    public void assignTable(int tableId) {
        System.out.println("Assigning table: " + tableId);
    }

    public void modifyOrder(String orderId) {
        System.out.println("Modifying order: " + orderId);
    }

    public void cancelOrder(String orderId) {
        System.out.println("Cancelling order: " + orderId);
    }

    @Override
    public void login() {
        System.out.println("Staff member " + getName() + " logged in.");
    }

    @Override
    public void logout() {
        System.out.println("Staff member " + getName() + " logged out.");
    }
}
