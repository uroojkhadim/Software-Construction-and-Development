package com.cafeteria.system;

public class Table {
    // Refactoring: Rename Field - Changed 'tableID' to 'id'
    private int id;
    private boolean isOccupied;
    private Status status;
    private Order activeOrder;

    public Table(int id) {
        this.id = id;
        this.isOccupied = false;
        this.status = Status.AVAILABLE;
        this.activeOrder = null;
    }

    public void assignTable(Order order) {
        this.isOccupied = true;
        this.status = Status.OCCUPIED;
        this.activeOrder = order;
        System.out.println("Table " + id + " assigned to order " + order.getId());
    }

    public void clearTable() {
        this.isOccupied = false;
        this.status = Status.AVAILABLE;
        this.activeOrder = null;
        System.out.println("Table " + id + " is now available.");
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public boolean isOccupied() { return isOccupied; }
    public void setOccupied(boolean isOccupied) { this.isOccupied = isOccupied; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public Order getActiveOrder() { return activeOrder; }
    public void setActiveOrder(Order activeOrder) { this.activeOrder = activeOrder; }
}
