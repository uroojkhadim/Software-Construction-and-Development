package com.cafeteria.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Refactoring: Extract Class / Split Large Class
 */
public class MenuManagementPanel extends JPanel {
    private JTextField nameInput, priceInput;
    private JComboBox<String> categoryCombo;
    private DefaultTableModel menuTableModel;
    private Runnable onMenuChange;

    public MenuManagementPanel(Runnable onMenuChange) {
        this.onMenuChange = onMenuChange;
        setLayout(new BorderLayout(10, 10));
        setBackground(UIUtils.BG_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createActionButtons(), BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Add / Update Menu Item"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameInput = new JTextField(15);
        priceInput = new JTextField(10);
        String[] categories = {"Coffee", "Snacks", "Desserts", "Beverages", "Main Course"};
        categoryCombo = new JComboBox<>(categories);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; formPanel.add(nameInput, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1; formPanel.add(priceInput, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1; formPanel.add(categoryCombo, gbc);

        return formPanel;
    }

    private JScrollPane createTablePanel() {
        String[] columns = {"ID", "Name", "Price", "Category"};
        menuTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        refreshTable();
        
        JTable table = new JTable(menuTableModel);
        return new JScrollPane(table);
    }

    private JPanel createActionButtons() {
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);

        JButton addBtn = new JButton("Add Item");
        UIUtils.styleButton(addBtn, UIUtils.ACCENT_COLOR);
        addBtn.addActionListener(e -> handleAdd());

        JButton updateBtn = new JButton("Update Selected");
        UIUtils.styleButton(updateBtn, UIUtils.PRIMARY_COLOR);
        // Add listener for update if needed (omitted for brevity in initial extraction)

        JButton deleteBtn = new JButton("Delete Selected");
        UIUtils.styleButton(deleteBtn, UIUtils.DANGER_COLOR);
        // Add listener for delete

        btnPanel.add(addBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(deleteBtn);
        return btnPanel;
    }

    private void handleAdd() {
        try {
            String name = nameInput.getText();
            double price = Double.parseDouble(priceInput.getText());
            String cat = (String) categoryCombo.getSelectedItem();
            if (!name.isEmpty()) {
                CafeteriaData data = CafeteriaData.getInstance();
                MenuItem newItem = new MenuItem(data.getMenuItems().size() + 1, name, price, cat, "");
                data.getMenuItems().add(newItem);
                refreshTable();
                onMenuChange.run();
                nameInput.setText(""); priceInput.setText("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid details.");
        }
    }

    public void refreshTable() {
        menuTableModel.setRowCount(0);
        for (MenuItem item : CafeteriaData.getInstance().getMenuItems()) {
            menuTableModel.addRow(new Object[]{item.getId(), item.getName(), String.format("%.2f", item.getPrice()), item.getCategory()});
        }
    }
}
