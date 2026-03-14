package com.cafeteria.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Refactoring: Extract Class / Split Large Class
 */
public class CustomerPanel extends JPanel {
    private JTextField nameInput, contactInput;
    private DefaultTableModel customerTableModel;
    private JTextArea orderHistoryArea;

    public CustomerPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(UIUtils.BG_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(createLeftPanel(), BorderLayout.WEST);
        add(createHistoryPanel(), BorderLayout.CENTER);
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setOpaque(false);
        leftPanel.setPreferredSize(new Dimension(400, 0));

        JPanel regForm = new JPanel(new GridBagLayout());
        regForm.setBackground(Color.WHITE);
        regForm.setBorder(BorderFactory.createTitledBorder("Register New Customer"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameInput = new JTextField(15);
        contactInput = new JTextField(15);
        gbc.gridx = 0; gbc.gridy = 0; regForm.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; regForm.add(nameInput, gbc);
        gbc.gridx = 0; gbc.gridy = 1; regForm.add(new JLabel("Contact:"), gbc);
        gbc.gridx = 1; regForm.add(contactInput, gbc);

        JButton regBtn = new JButton("Register Customer");
        UIUtils.styleButton(regBtn, UIUtils.ACCENT_COLOR);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        regForm.add(regBtn, gbc);
        regBtn.addActionListener(e -> handleRegistration());

        leftPanel.add(regForm, BorderLayout.NORTH);

        String[] cols = {"Name", "Contact", "Points"};
        customerTableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(customerTableModel);
        table.getSelectionModel().addListSelectionListener(e -> showHistory(table));
        leftPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        return leftPanel;
    }

    private JPanel createHistoryPanel() {
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBackground(Color.WHITE);
        historyPanel.setBorder(BorderFactory.createTitledBorder("Customer Order History"));
        
        orderHistoryArea = new JTextArea();
        orderHistoryArea.setEditable(false);
        orderHistoryArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        orderHistoryArea.setForeground(Color.BLACK); // Fix: Explicitly set text color to black for visibility
        historyPanel.add(new JScrollPane(orderHistoryArea), BorderLayout.CENTER);
        return historyPanel;
    }

    private void handleRegistration() {
        String name = nameInput.getText();
        String contact = contactInput.getText();
        if (!name.isEmpty()) {
            CafeteriaData data = CafeteriaData.getInstance();
            Customer nc = new Customer("C" + (data.getCustomers().size() + 1), name, contact);
            data.getCustomers().add(nc);
            customerTableModel.addRow(new Object[]{name, contact, 0});
            nameInput.setText(""); contactInput.setText("");
            JOptionPane.showMessageDialog(this, "Customer Registered!");
        }
    }

    private void showHistory(JTable table) {
        int row = table.getSelectedRow();
        if (row != -1) {
            String name = (String) customerTableModel.getValueAt(row, 0);
            for (Customer c : CafeteriaData.getInstance().getCustomers()) {
                if (c.getName().equals(name)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Order History for ").append(c.getName()).append("\n");
                    sb.append("Total Loyalty Points: ").append(c.getLoyaltyPoints()).append("\n");
                    sb.append("==========================================\n");
                    for (Order o : c.getOrderHistory()) {
                        sb.append("Order: ").append(o.getId())
                          .append(" | Total: $").append(String.format("%.2f", o.getGrandTotal()))
                          .append(" | Status: ").append(o.getStatus().getLabel()).append("\n");
                    }
                    orderHistoryArea.setText(sb.toString());
                    break;
                }
            }
        }
    }
}
