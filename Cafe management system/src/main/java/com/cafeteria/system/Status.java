package com.cafeteria.system;

// Refactoring: Replace Type Code with Enum - Created to eliminate 'Magic Strings' code smell
public enum Status {
    PENDING("Pending"),
    PREPARING("Preparing"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    AVAILABLE("Available"),
    OCCUPIED("Occupied");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
