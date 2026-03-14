package com.cafeteria.system;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * A custom JPanel to render simple bar charts for analytics.
 */
public class ChartPanel extends JPanel {
    private Map<String, Double> data = new LinkedHashMap<>();
    private String title = "";

    public ChartPanel() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
    }

    public void setData(String title, Map<String, Double> data) {
        this.title = title;
        this.data = data;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data == null || data.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int padding = 50;
        int labelPadding = 20;

        // Draw Title
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
        FontMetrics titleMetrics = g2.getFontMetrics();
        g2.drawString(title, (width - titleMetrics.stringWidth(title)) / 2, padding / 2);

        // Find max value for scaling
        double maxVal = data.values().stream().max(Double::compare).orElse(1.0);
        if (maxVal == 0) maxVal = 1.0;

        int chartWidth = width - 2 * padding;
        int chartHeight = height - 2 * padding - labelPadding;
        int barWidth = chartWidth / data.size() - 10;

        int x = padding;
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            double value = entry.getValue();
            int barHeight = (int) ((value / maxVal) * chartHeight);

            // Draw Bar
            g2.setColor(UIUtils.PRIMARY_COLOR);
            g2.fillRect(x, height - padding - barHeight, barWidth, barHeight);
            
            // Draw Outline
            g2.setColor(new Color(41, 128, 185));
            g2.drawRect(x, height - padding - barHeight, barWidth, barHeight);

            // Draw Label
            g2.setColor(Color.BLACK);
            String label = entry.getKey();
            if (label.length() > 10) label = label.substring(0, 8) + "..";
            FontMetrics labelMetrics = g2.getFontMetrics();
            g2.drawString(label, x + (barWidth - labelMetrics.stringWidth(label)) / 2, height - padding + 15);

            // Draw Value
            String valStr = String.format("%.1f", value);
            g2.drawString(valStr, x + (barWidth - labelMetrics.stringWidth(valStr)) / 2, height - padding - barHeight - 5);

            x += barWidth + 10;
        }
    }
}
