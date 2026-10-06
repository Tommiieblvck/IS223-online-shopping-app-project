package com.example.theodistonline_shoppingapp;

import java.util.Locale;

public class Product {
    protected String productName;
    protected double price;
    protected String category;
    protected int imageResId;

    public Product(String productName, double price) {
        this(productName, price, "General", android.R.drawable.ic_menu_gallery);
    }

    public Product(String productName, double price, String category) {
        this(productName, price, category, android.R.drawable.ic_menu_gallery);
    }

    public Product(String productName, double price, String category, int imageResId) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.imageResId = imageResId;
    }

    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public int getImageResId() { return imageResId; }

    public String displayProduct() {
        return productName + " - K" + String.format(Locale.getDefault(), "%.2f", price);
    }
}
