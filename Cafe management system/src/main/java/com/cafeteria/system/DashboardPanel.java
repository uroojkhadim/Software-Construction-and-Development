package com.cafeteria.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Refactoring: Extract Class (UI Component)
 * Part of splitting the Large Class 'MainFrame'.
 */
public class DashboardPanel extends JPanel {
    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(UIUtils.BG_COLOR);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        refresh();
    }

    public void refresh() {
        removeAll();
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        statsPanel.setOpaque(false);
        
        CafeteriaData data = CafeteriaData.getInstance();
        int activeOrders = (int) data.getAllOrders().stream().filter(o -> o.getStatus() == Status.PENDING || o.getStatus() == Status.PREPARING).count();
        int availTables = (int) data.getTables().stream().filter(t -> !t.isOccupied()).count();
        int lowStock = (int) data.getInventoryList().stream().filter(Inventory::isLowStock).count();
        double revenue = data.getAllOrders().stream().filter(o -> o.getStatus() == Status.COMPLETED).mapToDouble(Order::getGrandTotal).sum();

        statsPanel.add(UIUtils.createCard("Active Orders", String.valueOf(activeOrders), UIUtils.PRIMARY_COLOR));
        statsPanel.add(UIUtils.createCard("Available Tables", availTables + " / " + data.getTables().size(), UIUtils.ACCENT_COLOR));
        statsPanel.add(UIUtils.createCard("Low Stock Items", String.valueOf(lowStock), UIUtils.DANGER_COLOR));
        statsPanel.add(UIUtils.createCard("Today's Revenue", "$" + String.format("%.2f", revenue), new Color(155, 89, 182)));
        
        add(statsPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
