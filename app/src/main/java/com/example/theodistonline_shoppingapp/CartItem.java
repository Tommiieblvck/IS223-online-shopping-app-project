package com.example.theodistonline_shoppingapp;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void increase() { quantity++; }
    public void decrease() { if (quantity > 1) quantity--; }
    public double getSubtotal() { return product.getPrice() * quantity; }
}