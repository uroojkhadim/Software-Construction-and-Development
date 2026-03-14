package com.cafeteria.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Refactoring: Extract Class / Split Large Class
 */
public class ReportsPanel extends JPanel {
    private JTextArea reportDisplay;
    private ChartPanel chartPanel;

    public ReportsPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(UIUtils.BG_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(createSidebar(), BorderLayout.WEST);
        
        JPanel mainContent = new JPanel(new GridLayout(2, 1, 0, 15));
        mainContent.setOpaque(false);
        mainContent.add(createDisplayArea());
        
        chartPanel = new ChartPanel();
        mainContent.add(chartPanel);
        
        add(mainContent, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel nav = new JPanel(new GridLayout(0, 1, 5, 5));
        nav.setOpaque(false);
        nav.setPreferredSize(new Dimension(200, 0));

        String[] types = {"Daily Sales", "Monthly Revenue", "Best Selling Items", "Inventory Status"};
        SalesReport salesReport = new SalesReport(CafeteriaData.getInstance().getAllOrders());

        for (String type : types) {
            JButton btn = new JButton(type);
            UIUtils.styleButton(btn, UIUtils.SECONDARY_COLOR);
            btn.setForeground(Color.BLACK); // Fix: Ensure black text for visibility
            btn.addActionListener(e -> handleReportClick(type, salesReport));
            nav.add(btn);
        }
        return nav;
    }

    private JScrollPane createDisplayArea() {
        reportDisplay = new JTextArea();
        reportDisplay.setEditable(false);
        reportDisplay.setFont(new Font("Consolas", Font.PLAIN, 14));
        reportDisplay.setBackground(Color.WHITE);
        reportDisplay.setForeground(Color.BLACK); // Fix: Explicitly set text color to black for visibility
        reportDisplay.setBorder(new EmptyBorder(20, 20, 20, 20));
        return new JScrollPane(reportDisplay);
    }

    private void handleReportClick(String type, SalesReport report) {
        if (type.equals("Daily Sales")) {
            reportDisplay.setText(report.generateDailyReport());
            chartPanel.setData("Daily Revenue", report.getRevenueData());
        } else if (type.equals("Monthly Revenue")) {
            reportDisplay.setText(report.generateMonthlyReport());
            chartPanel.setData("Revenue Breakdown", report.getRevenueData());
        } else if (type.equals("Best Selling Items")) {
            reportDisplay.setText(report.generateTopItemsReport());
            chartPanel.setData("Top Items (Units)", report.getTopItemsData());
        } else {
            reportDisplay.setText("Inventory consumption and forecasting data.");
            chartPanel.setData("Inventory Status", new java.util.HashMap<>());
        }
    }
}
