package com.cafeteria.system;

// Refactoring: Replace Type Code with Enum - Solves 'Primitive Obsession' code smell
public enum UserRole {
    ADMIN("Administrator"),
    MANAGER("Manager"),
    CASHIER("Cashier"),
    WAITER("Waiter"),
    STAFF("General Staff");

    private final String label;

    UserRole(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
