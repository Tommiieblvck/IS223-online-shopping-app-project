package com.example.theodistonline_shoppingapp;

public class Electronics extends Product {
    public Electronics(String productName, double price) {
        super(productName, price, "Electronics");
    }

    @Override
    public String displayProduct() {
        return category + ": " + super.displayProduct();
    }
}
