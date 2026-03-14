package com.cafeteria.system;

public class MenuItem {
    // Refactoring: Rename Field - Changed 'itemID' to 'id' for better naming convention
    private int id;
    private String name;
    private double price;
    private String category;
    private String imageURL;

    public MenuItem(int id, String name, double price, String category, String imageURL) {
        // Code Smell: Data Validation - Added Guard Clause to prevent invalid negative prices
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.imageURL = imageURL;
    }

    public void addItem() {
        System.out.println("Adding menu item: " + name);
    }

    public void updateItem() {
        System.out.println("Updating menu item: " + name);
    }

    public void deleteItem() {
        System.out.println("Deleting menu item: " + name);
    }

    public void uploadImage(String url) {
        this.imageURL = url;
        System.out.println("Image uploaded for: " + name);
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getImageURL() { return imageURL; }
    public void setImageURL(String imageURL) { this.imageURL = imageURL; }
}
