package com.cafeteria.system;

public abstract class User {
    // Refactoring: Rename Field - Changed 'userID' to 'id' for consistency across all classes
    private String id;
    private String name;
    // Refactoring: Replace Type Code with Enum - Changed 'role' from String to UserRole to avoid 'Primitive Obsession'
    private UserRole role;
    private String password;

    public User(String id, String name, UserRole role, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
    }

    public abstract void login();
    public abstract void logout();

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
