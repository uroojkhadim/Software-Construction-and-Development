package com.cafeteria.system;

import javax.swing.*;
import java.awt.*;

public class NotificationManager {
    private static NotificationManager instance;
    private Component parent;

    private NotificationManager() {}

    public static NotificationManager getInstance() {
        if (instance == null) {
            instance = new NotificationManager();
        }
        return instance;
    }

    public void setParent(Component parent) {
        this.parent = parent;
    }

    public void showOrderReady(String orderId) {
        JOptionPane.showMessageDialog(parent, 
            "ORDER READY!\nOrder ID: " + orderId + "\nPlease serve the customer.", 
            "Order Notification", 
            JOptionPane.INFORMATION_MESSAGE);
        UserManager.getInstance().logActivity("Notification sent: Order " + orderId + " is ready.");
    }

    public void showLowStockAlert(String itemName, int currentQty) {
        JOptionPane.showMessageDialog(parent, 
            "LOW STOCK ALERT!\nItem: " + itemName + "\nCurrent Quantity: " + currentQty + "\nPlease restock soon.", 
            "Inventory Alert", 
            JOptionPane.WARNING_MESSAGE);
        UserManager.getInstance().logActivity("Notification sent: Low stock alert for " + itemName);
    }
}
