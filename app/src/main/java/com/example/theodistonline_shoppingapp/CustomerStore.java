package com.example.theodistonline_shoppingapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manages the current user session and authentication operations using UserDatabase.
 */
public class CustomerStore {
    private static Customer current;

    public static Customer login(Context context, String usernameOrEmail, String password) {
        UserDatabase db = new UserDatabase(context);
        Customer customer = db.loginUser(usernameOrEmail, password);
        if (customer != null) {
            current = customer;
        }
        return customer;
    }

    public static Customer getCustomerByUsername(Context context, String username) {
        UserDatabase db = new UserDatabase(context);
        Customer customer = db.getUserByUsername(username);
        if (customer != null) {
            current = customer;
        }
        return customer;
    }

    public static boolean register(Context context, String fullName, String username, String email, String phone, String location, String password) {
        UserDatabase db = new UserDatabase(context);
        return db.registerUser(fullName, username, email, phone, location, password);
    }

    public static Customer getCurrent() {
        if (current == null) {
            // Default fallback for demo / testing if not logged in
            current = new Customer(1, "John Rambo", "johnrambo", "john@theodist.com", "+675 323 1234", "Port Moresby");
        }
        return current;
    }

    public static void setCurrent(Customer customer) {
        current = customer;
    }

    public static void logout(Context context) {
        current = null;
        if (context != null) {
            SharedPreferences prefs = context.getSharedPreferences("TheodistPrefs", Context.MODE_PRIVATE);
            prefs.edit().remove("remembered_username").apply();
        }
    }
}
