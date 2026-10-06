package com.example.theodistonline_shoppingapp;

public class Clothing extends Product {
    public Clothing(String productName, double price) {
        super(productName, price, "Clothing");
    }

    @Override
    public String displayProduct() {
        return category + ": " + super.displayProduct();
    }
}
