package com.example.theodistonline_shoppingapp;

import java.util.Locale;

public class Stationary extends Product {
    public Stationary(String productName, double price) {
        super(productName, price, "Stationary");
    }

    public Stationary(String productName, double price, int imageResId) {
        super(productName, price, "Stationary", imageResId);
    }

    @Override
    public String displayProduct() {
        return "Stationary: " + productName + " - K" + String.format(Locale.getDefault(), "%.2f", price);
    }
}
