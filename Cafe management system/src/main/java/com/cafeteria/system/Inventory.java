package com.cafeteria.system;

public class Inventory {
    // Refactoring: Rename Field - Changed 'ingredientID' to 'id'
    private String id;
    private int itemId;
    private int stockQuantity;
    private int lowStockThreshold;
    private String supplierName;

    public Inventory(String id, int itemId, int stockQuantity, int lowStockThreshold, String supplierName) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.id = id;
        this.itemId = itemId;
        this.stockQuantity = stockQuantity;
        this.lowStockThreshold = lowStockThreshold;
        this.supplierName = supplierName;
    }

    public boolean isLowStock() {
        return stockQuantity <= lowStockThreshold;
    }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public int getLowStockThreshold() { return lowStockThreshold; }
    public void setLowStockThreshold(int lowStockThreshold) { this.lowStockThreshold = lowStockThreshold; }

    public void updateStock(int quantity) {
        if (this.stockQuantity + quantity < 0) {
            throw new IllegalArgumentException("Resulting stock cannot be negative");
        }
        this.stockQuantity += quantity;
        System.out.println("Stock updated. New quantity: " + stockQuantity);
    }

    public void alertLowStock() {
        if (stockQuantity < 10) {
            System.out.println("Alert: Low stock for ingredient " + id);
        }
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.stockQuantity = stockQuantity;
    }
}
