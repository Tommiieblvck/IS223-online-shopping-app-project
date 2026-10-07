package com.example.theodistonline_shoppingapp;

import java.util.Locale;

public class OfficeFurniture extends Product {
    public OfficeFurniture(String productName, double price) {
        super(productName, price, "Office Furniture");
    }

    public OfficeFurniture(String productName, double price, int imageResId) {
        super(productName, price, "Office Furniture", imageResId);
    }

    @Override
    public String displayProduct() {
        return "Office Furniture: " + productName + " - K" + String.format(Locale.getDefault(), "%.2f", price);
    }
}
