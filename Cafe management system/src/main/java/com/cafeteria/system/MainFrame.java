package com.cafeteria.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Refactoring: Split Large Class
 * MainFrame now only handles high-level navigation and layout.
 * Business logic and specific UI construction are delegated to specialized panels.
 */
public class MainFrame extends JFrame {
    private JPanel mainContent;
    private CardLayout cardLayout;
    private List<JButton> sidebarButtons = new ArrayList<>();
    private JPanel sidebar;

    // Modular Panels
    private DashboardPanel dashboardPanel;
    private OrderPanel orderPanel;
    private TablePanel tablePanel;
    private CustomerPanel customerPanel;
    private StaffPanel staffPanel;
    private MenuManagementPanel menuMgmtPanel;
    private ReportsPanel reportsPanel;
    private InventoryPanel inventoryPanel;

    public MainFrame() {
        setTitle("Cafeteria Management System - Refactored");
        setSize(1200, 800);
        setMinimumSize(new Dimension(600, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        NotificationManager.getInstance().setParent(this);
        
        initPanels();
        add(createHeader(), BorderLayout.NORTH);
        sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) { handleResponsiveness(); }
        });

        showPanel("Dashboard");
    }

    private void initPanels() {
        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);
        
        dashboardPanel = new DashboardPanel();
        orderPanel = new OrderPanel(() -> dashboardPanel.refresh());
        tablePanel = new TablePanel();
        customerPanel = new CustomerPanel();
        staffPanel = new StaffPanel();
        menuMgmtPanel = new MenuManagementPanel(() -> orderPanel.refreshMenu());
        inventoryPanel = new InventoryPanel();
        reportsPanel = new ReportsPanel();

        mainContent.add(dashboardPanel, "Dashboard");
        mainContent.add(orderPanel, "Menu");
        mainContent.add(tablePanel, "Tables");
        mainContent.add(customerPanel, "Customers");
        mainContent.add(staffPanel, "Manage Staff");
        mainContent.add(menuMgmtPanel, "Manage Menu");
        mainContent.add(inventoryPanel, "Inventory");
        mainContent.add(reportsPanel, "Reports");
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(41, 128, 185));
        header.setPreferredSize(new Dimension(getWidth(), 50));
        header.setBorder(new EmptyBorder(0, 15, 0, 15));

        JLabel title = new JLabel("Cafeteria Management System", JLabel.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(title, BorderLayout.WEST);
        
        User user = UserManager.getInstance().getCurrentUser();
        String info = (user != null) ? user.getName() + " (" + user.getRole().getLabel() + ")" : "Guest";
        JLabel userLbl = new JLabel(info, JLabel.RIGHT);
        userLbl.setForeground(Color.WHITE);
        header.add(userLbl, BorderLayout.EAST);
        
        return header;
    }

    private JPanel createSidebar() {
        JPanel bar = new JPanel();
        bar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
        bar.setBackground(UIUtils.SECONDARY_COLOR);
        bar.setPreferredSize(new Dimension(220, getHeight()));
        bar.setBorder(new EmptyBorder(20, 0, 0, 0));

        List<String> nav = new ArrayList<>();
        nav.add("Dashboard"); nav.add("Menu"); nav.add("Tables"); nav.add("Customers");
        
        if (UserManager.getInstance().isAdmin()) {
            nav.add("Manage Staff"); nav.add("Manage Menu"); nav.add("Inventory"); nav.add("Reports");
        }
        nav.add("Logout");

        for (String text : nav) {
            JButton btn = createNavButton(text);
            bar.add(btn);
            sidebarButtons.add(btn);
        }
        return bar;
    }

    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setName(text);
        btn.setPreferredSize(new Dimension(200, 50));
        btn.setForeground(new Color(189, 195, 199));
        btn.setBackground(UIUtils.SECONDARY_COLOR);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMargin(new Insets(0, 20, 0, 0));
        
        btn.addActionListener(e -> {
            if (text.equals("Logout")) handleLogout();
            else showPanel(text);
        });
        return btn;
    }

    private void handleLogout() {
        if (JOptionPane.showConfirmDialog(this, "Logout?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            UserManager.getInstance().logout();
            dispose();
            startApplication();
        }
    }

    private void showPanel(String name) {
        cardLayout.show(mainContent, name);
        if (name.equals("Dashboard")) dashboardPanel.refresh();
        if (name.equals("Menu")) { orderPanel.refreshCustomers(); orderPanel.refreshTables(); }
        if (name.equals("Tables")) tablePanel.refresh();
        if (name.equals("Inventory")) inventoryPanel.refreshTable();
        
        for (JButton btn : sidebarButtons) {
            boolean active = btn.getText().equals(name);
            btn.setBackground(active ? UIUtils.PRIMARY_COLOR : UIUtils.SECONDARY_COLOR);
            btn.setForeground(active ? Color.WHITE : new Color(189, 195, 199));
        }
    }

    private void handleResponsiveness() {
        int w = getWidth();
        int barW = (w < 600) ? 60 : (w < 900) ? 150 : 220;
        sidebar.setPreferredSize(new Dimension(barW, getHeight()));
        for (JButton b : sidebarButtons) {
            b.setText(w < 600 ? "" : b.getName());
            b.setPreferredSize(new Dimension(barW - 20, 50));
        }
        sidebar.revalidate();
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        startApplication();
    }

    private static void startApplication() {
        SwingUtilities.invokeLater(() -> {
            LoginDialog login = new LoginDialog(null);
            login.setVisible(true);
            if (login.isAuthenticated()) new MainFrame().setVisible(true);
            else System.exit(0);
        });
    }
}
