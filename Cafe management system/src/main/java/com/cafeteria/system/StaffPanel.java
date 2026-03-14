package com.cafeteria.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Refactoring: Extract Class / Split Large Class
 */
public class StaffPanel extends JPanel {
    private JTextField idInput, nameInput, passwordInput;
    private JComboBox<UserRole> roleCombo;
    private DefaultTableModel staffTableModel;
    private JTextArea activityLogArea;

    public StaffPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(UIUtils.BG_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(createRegistrationForm(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
    }

    private JPanel createRegistrationForm() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Register New Staff"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        idInput = new JTextField(10);
        nameInput = new JTextField(15);
        passwordInput = new JTextField(10);
        roleCombo = new JComboBox<>(UserRole.values());

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("User ID:"), gbc);
        gbc.gridx = 1; formPanel.add(idInput, gbc);
        gbc.gridx = 2; gbc.gridy = 0; formPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 3; formPanel.add(nameInput, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; formPanel.add(passwordInput, gbc);
        gbc.gridx = 2; gbc.gridy = 1; formPanel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 3; formPanel.add(roleCombo, gbc);

        JButton addStaffBtn = new JButton("Add Staff");
        UIUtils.styleButton(addStaffBtn, UIUtils.ACCENT_COLOR);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 4;
        formPanel.add(addStaffBtn, gbc);
        addStaffBtn.addActionListener(e -> handleAddStaff());

        return formPanel;
    }

    private JPanel createMainContent() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        centerPanel.setOpaque(false);

        // Staff List
        String[] cols = {"ID", "Name", "Role"};
        staffTableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(staffTableModel);
        table.setRowHeight(30);
        refreshStaffTable();
        centerPanel.add(new JScrollPane(table));

        // Activity Log
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBackground(Color.WHITE);
        logPanel.setBorder(BorderFactory.createTitledBorder("Staff Activity Log"));
        activityLogArea = new JTextArea();
        activityLogArea.setEditable(false);
        activityLogArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        activityLogArea.setForeground(Color.BLACK); // Fix: Explicitly set text color to black for visibility
        logPanel.add(new JScrollPane(activityLogArea), BorderLayout.CENTER);
        
        JButton refreshLogBtn = new JButton("Refresh Activity Log");
        refreshLogBtn.addActionListener(e -> refreshActivityLog());
        logPanel.add(refreshLogBtn, BorderLayout.SOUTH);
        
        centerPanel.add(logPanel);
        return centerPanel;
    }

    private void handleAddStaff() {
        String id = idInput.getText();
        String name = nameInput.getText();
        String pass = passwordInput.getText();
        UserRole role = (UserRole) roleCombo.getSelectedItem();
        
        if (!id.isEmpty() && !name.isEmpty()) {
            User newUser;
            if (role == UserRole.ADMIN) newUser = new Admin(id, name, pass);
            else if (role == UserRole.CASHIER) newUser = new Cashier(id, name, pass);
            else newUser = new Staff(id, name, pass);
            
            UserManager.getInstance().registerUser(newUser);
            staffTableModel.addRow(new Object[]{id, name, role.getLabel()});
            UserManager.getInstance().logActivity("Admin registered new staff: " + name + " as " + role.getLabel());
            idInput.setText(""); nameInput.setText(""); passwordInput.setText("");
        }
    }

    private void refreshStaffTable() {
        staffTableModel.setRowCount(0);
        for (User u : UserManager.getInstance().getAllUsers()) {
            staffTableModel.addRow(new Object[]{u.getId(), u.getName(), u.getRole().getLabel()});
        }
    }

    public void refreshActivityLog() {
        StringBuilder sb = new StringBuilder();
        for (String activity : UserManager.getInstance().getActivities()) {
            sb.append(activity).append("\n");
        }
        activityLogArea.setText(sb.toString());
    }
}
