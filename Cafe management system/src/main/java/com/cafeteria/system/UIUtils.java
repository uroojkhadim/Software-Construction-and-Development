package com.cafeteria.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Refactoring: Extract Class / Utility Class
 * Addresses 'Duplicate Code' code smell by centralizing UI styling logic.
 */
public class UIUtils {
    public static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    public static final Color SECONDARY_COLOR = new Color(44, 62, 80);
    public static final Color ACCENT_COLOR = new Color(46, 204, 113);
    public static final Color DANGER_COLOR = new Color(231, 76, 60);
    public static final Color BG_COLOR = new Color(236, 240, 241);
    public static final Color TEXT_COLOR = new Color(44, 62, 80);

    public static void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(0, 40));
        btn.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
    }

    public static JPanel createCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(200, 100));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
            new EmptyBorder(10, 15, 10, 15)
        ));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        titleLbl.setForeground(new Color(100, 100, 100));
        
        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLbl.setForeground(color);

        card.add(titleLbl, BorderLayout.NORTH);
        card.add(valueLbl, BorderLayout.CENTER);
        
        return card;
    }
}
