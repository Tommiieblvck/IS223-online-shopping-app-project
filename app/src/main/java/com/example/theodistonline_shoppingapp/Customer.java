package com.example.theodistonline_shoppingapp;

/**
 * Customer model representing a registered user in Theodist Super Store.
 */
public class Customer {
    private long id;
    private String fullName;
    private String username;
    private String email;
    private String phone;
    private String location;

    public Customer(long id, String fullName, String username, String email, String phone, String location) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.location = location;
    }

    public Customer(String fullName, String username, String password) {
        this(-1, fullName, username, username + "@theodist.com", "", "");
    }

    public long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getLocation() { return location; }
}
