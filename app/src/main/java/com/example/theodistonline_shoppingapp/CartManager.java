package com.example.theodistonline_shoppingapp;

public class CartManager {
    private static Cart cart = new Cart();

    public static Cart getCart() {
        return cart;
    }
}