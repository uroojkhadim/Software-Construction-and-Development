package com.cafeteria.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Refactoring: Extract Class / Split Large Class
 */
public class InventoryPanel extends JPanel {
    private DefaultTableModel inventoryTableModel;
    private JTextField itemIdInput, quantityInput, thresholdInput, supplierInput;

    public InventoryPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(UIUtils.BG_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createActionButtons(), BorderLayout.SOUTH);
        
        refreshTable();
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Manage Stock & Suppliers"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        itemIdInput = new JTextField(10);
        quantityInput = new JTextField(5);
        thresholdInput = new JTextField(5);
        supplierInput = new JTextField(15);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Item ID/Name:"), gbc);
        gbc.gridx = 1; formPanel.add(itemIdInput, gbc);
        gbc.gridx = 2; gbc.gridy = 0; formPanel.add(new JLabel("Stock Qty:"), gbc);
        gbc.gridx = 3; formPanel.add(quantityInput, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Threshold:"), gbc);
        gbc.gridx = 1; formPanel.add(thresholdInput, gbc);
        gbc.gridx = 2; gbc.gridy = 1; formPanel.add(new JLabel("Supplier:"), gbc);
        gbc.gridx = 3; formPanel.add(supplierInput, gbc);

        return formPanel;
    }

    private JScrollPane createTablePanel() {
        String[] cols = {"ID", "Item ID", "Qty", "Threshold", "Supplier", "Status"};
        inventoryTableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        JTable table = new JTable(inventoryTableModel);
        table.setRowHeight(30);
        return new JScrollPane(table);
    }

    private JPanel createActionButtons() {
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);

        JButton addUpdateBtn = new JButton("Add/Update Stock");
        UIUtils.styleButton(addUpdateBtn, UIUtils.ACCENT_COLOR);
        addUpdateBtn.addActionListener(e -> handleAddUpdate());

        JButton restockAllBtn = new JButton("Quick Restock All (+20)");
        UIUtils.styleButton(restockAllBtn, UIUtils.PRIMARY_COLOR);
        restockAllBtn.addActionListener(e -> {
            CafeteriaData.getInstance().getInventoryList().forEach(inv -> inv.updateStock(20));
            refreshTable();
        });

        btnPanel.add(addUpdateBtn);
        btnPanel.add(restockAllBtn);
        return btnPanel;
    }

    private void handleAddUpdate() {
        try {
            String id = itemIdInput.getText();
            int qty = Integer.parseInt(quantityInput.getText());
            int threshold = Integer.parseInt(thresholdInput.getText());
            String supplier = supplierInput.getText();
            
            CafeteriaData data = CafeteriaData.getInstance();
            boolean exists = false;
            for (Inventory inv : data.getInventoryList()) {
                if (inv.getId().equalsIgnoreCase(id)) {
                    inv.setStockQuantity(qty);
                    inv.setLowStockThreshold(threshold);
                    inv.setSupplierName(supplier);
                    exists = true;
                    if (inv.isLowStock()) NotificationManager.getInstance().showLowStockAlert(id, qty);
                    break;
                }
            }
            
            if (!exists) {
                Inventory newInv = new Inventory(id, 0, qty, threshold, supplier);
                data.getInventoryList().add(newInv);
                if (newInv.isLowStock()) NotificationManager.getInstance().showLowStockAlert(id, qty);
            }
            
            refreshTable();
            clearInputs();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Enter valid numeric values for Qty and Threshold.");
        }
    }

    private void clearInputs() {
        itemIdInput.setText(""); quantityInput.setText(""); thresholdInput.setText(""); supplierInput.setText("");
    }

    public void refreshTable() {
        if (inventoryTableModel == null) return;
        inventoryTableModel.setRowCount(0);
        for (Inventory inv : CafeteriaData.getInstance().getInventoryList()) {
            String status = inv.isLowStock() ? "LOW STOCK" : "OK";
            inventoryTableModel.addRow(new Object[]{
                inv.getId(), inv.getItemId(), inv.getStockQuantity(), 
                inv.getLowStockThreshold(), inv.getSupplierName(), status
            });
        }
    }
}
