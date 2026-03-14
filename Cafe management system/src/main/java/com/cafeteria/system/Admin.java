package com.cafeteria.system;

public class Admin extends User {

    public Admin(String id, String name, String password) {
        // Refactoring: Use Enum instead of String literal
        super(id, name, UserRole.ADMIN, password);
    }

    public void createUser(User user) {
        System.out.println("Creating user: " + user.getName());
    }

    public void updateUser(User user) {
        System.out.println("Updating user: " + user.getName());
    }

    public void deleteUser(String id) {
        System.out.println("Deleting user with ID: " + id);
    }

    @Override
    public void login() {
        System.out.println("Admin " + getName() + " logged in.");
    }

    @Override
    public void logout() {
        System.out.println("Admin " + getName() + " logged out.");
    }
}
