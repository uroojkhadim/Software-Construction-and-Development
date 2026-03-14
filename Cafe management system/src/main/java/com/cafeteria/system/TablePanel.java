package com.cafeteria.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Refactoring: Extract Class / Split Large Class
 */
public class TablePanel extends JPanel {
    public TablePanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(UIUtils.BG_COLOR);
        setBorder(new EmptyBorder(15, 15, 15, 15));
        refresh();
    }

    public void refresh() {
        removeAll();
        CafeteriaData data = CafeteriaData.getInstance();
        
        if (UserManager.getInstance().isAdmin()) {
            add(createAdminControls(), BorderLayout.NORTH);
        }

        JPanel grid = new JPanel(new GridLayout(0, 4, 10, 10));
        grid.setOpaque(false);
        for (Table t : data.getTables()) {
            JButton tBtn = createTableButton(t);
            grid.add(tBtn);
        }
        add(new JScrollPane(grid), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private JPanel createAdminControls() {
        JPanel adminPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        adminPanel.setBackground(UIUtils.BG_COLOR);
        adminPanel.add(new JLabel("Manage Tables:"));
        
        JTextField tableIdInput = new JTextField(5);
        JButton addBtn = new JButton("Add Table");
        JButton removeBtn = new JButton("Remove Table");
        
        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tableIdInput.getText());
                CafeteriaData.getInstance().getTables().add(new Table(id));
                refresh();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Enter valid number."); }
        });
        
        removeBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(tableIdInput.getText());
                CafeteriaData.getInstance().getTables().removeIf(t -> t.getId() == id);
                refresh();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Enter valid number."); }
        });
        
        adminPanel.add(tableIdInput); adminPanel.add(addBtn); adminPanel.add(removeBtn);
        return adminPanel;
    }

    private JButton createTableButton(Table t) {
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(100, 100));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        updateButtonStyle(btn, t);
        
        btn.addActionListener(e -> {
            if (t.isOccupied()) {
                handleOccupiedClick(t, btn);
            } else {
                JOptionPane.showMessageDialog(this, "Table " + t.getId() + " is available.");
            }
        });
        return btn;
    }

    private void handleOccupiedClick(Table t, JButton btn) {
        Order o = t.getActiveOrder();
        String info = "Order ID: " + o.getId() + "\nTotal: $" + String.format("%.2f", o.getGrandTotal()) + "\nClear table?";
        int choice = JOptionPane.showConfirmDialog(this, info, "Table " + t.getId(), JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            t.clearTable();
            updateButtonStyle(btn, t);
        }
    }

    private void updateButtonStyle(JButton btn, Table t) {
        if (t.isOccupied()) {
            btn.setBackground(new Color(255, 107, 107));
            btn.setText("<html><center><font color='black'>Table " + t.getId() + "<br>(Occupied)</font></center></html>");
        } else {
            btn.setBackground(new Color(123, 237, 159));
            btn.setText("<html><center><font color='black'>Table " + t.getId() + "<br>(Available)</font></center></html>");
        }
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
    }
}
