package com.cafeteria.system;

import java.util.ArrayList;
import java.util.List;

/**
 * Refactoring: Extract Class (Data Repository)
 * Addresses 'Large Class' by moving data management logic out of MainFrame.
 */
public class CafeteriaData {
    private static CafeteriaData instance;
    private List<MenuItem> menuItems = new ArrayList<>();
    private List<Table> tables = new ArrayList<>();
    private List<Inventory> inventoryList = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Order> allOrders = new ArrayList<>();

    private CafeteriaData() {
        initializeData();
    }

    public static CafeteriaData getInstance() {
        if (instance == null) {
            instance = new CafeteriaData();
        }
        return instance;
    }

    private void initializeData() {
        menuItems.add(new MenuItem(1, "Espresso", 2.50, "Coffee", ""));
        menuItems.add(new MenuItem(2, "Cappuccino", 3.50, "Coffee", ""));
        menuItems.add(new MenuItem(3, "Latte", 3.75, "Coffee", ""));
        menuItems.add(new MenuItem(4, "Chicken Sandwich", 5.50, "Food", ""));
        menuItems.add(new MenuItem(5, "Chocolate Muffin", 2.25, "Food", ""));
        
        for (int i = 1; i <= 12; i++) tables.add(new Table(i));
        
        inventoryList.add(new Inventory("I001", 1, 50, 10, "Global Beans Co."));
        inventoryList.add(new Inventory("I002", 4, 5, 10, "Fresh Bakes Ltd."));
    }

    public List<MenuItem> getMenuItems() { return menuItems; }
    public List<Table> getTables() { return tables; }
    public List<Inventory> getInventoryList() { return inventoryList; }
    public List<Customer> getCustomers() { return customers; }
    public List<Order> getAllOrders() { return allOrders; }
}
