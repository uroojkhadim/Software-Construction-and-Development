package com.cafeteria.system;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginDialog extends JDialog {
    private JTextField idField;
    private JPasswordField passwordField;
    private boolean authenticated = false;

    public LoginDialog(Frame parent) {
        super(parent, "System Login", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;

        JLabel title = new JLabel("Cafeteria Login", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(44, 62, 80));
        gbc.gridy = 0;
        panel.add(title, gbc);

        idField = new JTextField();
        idField.setPreferredSize(new Dimension(0, 35));
        idField.setBorder(BorderFactory.createTitledBorder("User ID"));
        gbc.gridy = 1;
        panel.add(idField, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(0, 35));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBackground(new Color(52, 152, 219));
        loginBtn.setForeground(Color.BLACK);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setPreferredSize(new Dimension(0, 40));
        loginBtn.addActionListener(e -> handleLogin());
        gbc.gridy = 3;
        panel.add(loginBtn, gbc);

        add(panel, BorderLayout.CENTER);

        // Enter key to login
        getRootPane().setDefaultButton(loginBtn);
    }

    private void handleLogin() {
        String id = idField.getText();
        String password = new String(passwordField.getPassword());

        if (UserManager.getInstance().authenticate(id, password)) {
            authenticated = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid ID or Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
