package com.cafeteria.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Refactoring: Extract Class / Split Large Class
 */
public class OrderPanel extends JPanel {
    private DefaultListModel<String> menuListModel;
    private JTextArea receiptArea;
    private JLabel totalDisplay;
    private JTextField taxInput, discountInput;
    private JComboBox<String> customerDropdown, tableDropdown;
    private JComboBox<PaymentMethod> paymentDropdown;
    
    private Order currentOrder;
    private Customer selectedCustomer;
    private Table selectedTable;
    private PaymentMethod selectedPayment = PaymentMethod.CASH;
    private Runnable onOrderConfirmed;

    public OrderPanel(Runnable onOrderConfirmed) {
        this.onOrderConfirmed = onOrderConfirmed;
        this.currentOrder = new Order("ORD-001", 1);
        setLayout(new BorderLayout(20, 0));
        setBackground(UIUtils.BG_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(createMenuSelectionPanel(), BorderLayout.CENTER);
        add(createSummaryPanel(), BorderLayout.EAST);
        
        refreshMenu();
    }

    private JPanel createMenuSelectionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Cafeteria Menu"));
        
        menuListModel = new DefaultListModel<>();
        JList<String> list = new JList<>(menuListModel);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        list.setFixedCellHeight(40);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JButton addBtn = new JButton("Add Selected to Order");
        UIUtils.styleButton(addBtn, UIUtils.PRIMARY_COLOR);
        addBtn.addActionListener(e -> {
            int idx = list.getSelectedIndex();
            if (idx != -1) {
                currentOrder.addMenuItem(CafeteriaData.getInstance().getMenuItems().get(idx));
                updateReceipt();
            }
        });
        panel.add(addBtn, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(400, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Current Order Summary"));

        // Selection Controls
        JPanel controls = new JPanel(new GridLayout(3, 1));
        controls.setBackground(Color.WHITE);
        
        customerDropdown = new JComboBox<>();
        refreshCustomers();
        controls.add(createLabeledCombo("Customer:", customerDropdown));
        
        tableDropdown = new JComboBox<>();
        refreshTables();
        controls.add(createLabeledCombo("Table:", tableDropdown));
        
        paymentDropdown = new JComboBox<>(PaymentMethod.values());
        controls.add(createLabeledCombo("Payment:", paymentDropdown));
        
        panel.add(controls, BorderLayout.NORTH);

        // Receipt Area
        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        receiptArea.setBackground(new Color(245, 245, 245));
        receiptArea.setForeground(Color.BLACK); // Fix: Explicitly set text color to black for visibility
        panel.add(new JScrollPane(receiptArea), BorderLayout.CENTER);

        // Billing and Footer
        panel.add(createFooter(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);

        JPanel billing = new JPanel(new GridLayout(2, 2, 5, 5));
        billing.setBackground(Color.WHITE);
        taxInput = new JTextField("0.00");
        discountInput = new JTextField("0.00");
        billing.add(new JLabel("Tax ($):")); billing.add(taxInput);
        billing.add(new JLabel("Discount ($):")); billing.add(discountInput);
        
        taxInput.addActionListener(e -> syncFinancials());
        discountInput.addActionListener(e -> syncFinancials());

        totalDisplay = new JLabel("Total: $0.00");
        totalDisplay.setFont(new Font("Segoe UI", Font.BOLD, 22));
        
        JButton confirmBtn = new JButton("CONFIRM ORDER");
        UIUtils.styleButton(confirmBtn, UIUtils.ACCENT_COLOR);
        confirmBtn.addActionListener(e -> handleConfirm());

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(totalDisplay, BorderLayout.WEST);
        bottom.add(confirmBtn, BorderLayout.EAST);

        footer.add(billing, BorderLayout.NORTH);
        footer.add(bottom, BorderLayout.SOUTH);
        return footer;
    }

    private JPanel createLabeledCombo(String label, JComboBox<?> combo) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setBackground(Color.WHITE);
        p.add(new JLabel(label));
        p.add(combo);
        return p;
    }

    private void handleConfirm() {
        if (currentOrder.getGrandTotal() <= 0) {
            JOptionPane.showMessageDialog(this, "Order is empty.");
            return;
        }
        
        CafeteriaData.getInstance().getAllOrders().add(currentOrder);
        UserManager.getInstance().logActivity("Order confirmed: " + currentOrder.getId());
        
        int choice = JOptionPane.showConfirmDialog(this, "Download receipt?", "Confirmed", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) saveReceipt();

        currentOrder = new Order("ORD-" + System.currentTimeMillis(), 1);
        clearInputs();
        updateReceipt();
        onOrderConfirmed.run();
    }

    private void saveReceipt() {
        String fileName = "Receipt_" + currentOrder.getId() + ".txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(receiptArea.getText());
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void clearInputs() {
        taxInput.setText("0.00"); discountInput.setText("0.00");
    }

    public void refreshMenu() {
        menuListModel.clear();
        CafeteriaData.getInstance().getMenuItems().forEach(i -> menuListModel.addElement(i.getName() + " - $" + i.getPrice()));
    }

    public void refreshCustomers() {
        customerDropdown.removeAllItems();
        customerDropdown.addItem("Guest");
        CafeteriaData.getInstance().getCustomers().forEach(c -> customerDropdown.addItem(c.getName()));
    }

    public void refreshTables() {
        tableDropdown.removeAllItems();
        tableDropdown.addItem("None/Takeaway");
        CafeteriaData.getInstance().getTables().forEach(t -> tableDropdown.addItem("Table " + t.getId()));
    }

    private void syncFinancials() {
        try {
            currentOrder.setTax(Double.parseDouble(taxInput.getText()));
            currentOrder.setDiscount(Double.parseDouble(discountInput.getText()));
            currentOrder.calculateTotal();
            updateReceipt();
        } catch (Exception e) {}
    }

    private void updateReceipt() {
        StringBuilder sb = new StringBuilder(" CAFETERIA RECEIPT\n =============================\n");
        currentOrder.getMenuItems().forEach(i -> sb.append(String.format(" %-20s $%.2f\n", i.getName(), i.getPrice())));
        sb.append(" -----------------------------\n");
        sb.append(String.format(" GRAND TOTAL: $%.2f\n =============================\n", currentOrder.getGrandTotal()));
        receiptArea.setText(sb.toString());
        totalDisplay.setText("Total: $" + String.format("%.2f", currentOrder.getGrandTotal()));
    }
}
