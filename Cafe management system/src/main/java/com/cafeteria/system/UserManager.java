package com.cafeteria.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManager {
    private static UserManager instance;
    private Map<String, User> users = new HashMap<>();
    private List<String> activities = new ArrayList<>();
    private User currentUser;

    private UserManager() {
        // Pre-register default users for testing
        registerUser(new Admin("admin", "System Admin", "admin123"));
        registerUser(new Staff("staff", "Cafeteria Staff", "staff123"));
        registerUser(new Cashier("cashier", "Main Cashier", "cashier123"));
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void logActivity(String activity) {
        String log = new java.util.Date() + ": " + activity;
        activities.add(log);
        System.out.println(log);
    }

    public List<String> getActivities() {
        return new ArrayList<>(activities);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void removeUser(String id) {
        users.remove(id);
    }

    public void registerUser(User user) {
        users.put(user.getId(), user);
    }

    public boolean authenticate(String id, String password) {
        User user = users.get(id);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            user.login();
            logActivity("User logged in: " + user.getName() + " (" + user.getRole().getLabel() + ")");
            return true;
        }
        return false;
    }

    public void logout() {
        if (currentUser != null) {
            logActivity("User logged out: " + currentUser.getName());
            currentUser.logout();
            currentUser = null;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isAdmin() {
        return currentUser != null && currentUser.getRole() == UserRole.ADMIN;
    }
}
