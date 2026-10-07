package com.example.theodistonline_shoppingapp;

import java.util.Locale;

public class Toys extends Product {
    public Toys(String productName, double price) {
        super(productName, price, "Toys");
    }

    public Toys(String productName, double price, int imageResId) {
        super(productName, price, "Toys", imageResId);
    }

    @Override
    public String displayProduct() {
        return "Toys: " + productName + " - K" + String.format(Locale.getDefault(), "%.2f", price);
    }
}
