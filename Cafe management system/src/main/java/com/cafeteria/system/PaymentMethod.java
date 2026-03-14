package com.cafeteria.system;

/**
 * Refactoring: Replace Type Code with Enum
 * Created to manage payment types (Cash, Card, Online) without using Magic Strings.
 */
public enum PaymentMethod {
    CASH("Cash"),
    CARD("Credit/Debit Card"),
    ONLINE("Online Payment");

    private final String label;

    PaymentMethod(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
