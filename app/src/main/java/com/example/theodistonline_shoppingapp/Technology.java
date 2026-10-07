package com.example.theodistonline_shoppingapp;

import java.util.Locale;

public class Technology extends Product {
    public Technology(String productName, double price) {
        super(productName, price, "Technology");
    }

    public Technology(String productName, double price, int imageResId) {
        super(productName, price, "Technology", imageResId);
    }

    @Override
    public String displayProduct() {
        return "Technology: " + productName + " - K" + String.format(Locale.getDefault(), "%.2f", price);
    }
}
