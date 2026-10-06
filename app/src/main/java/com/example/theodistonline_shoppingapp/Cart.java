package com.example.theodistonline_shoppingapp;

import java.util.ArrayList;

public class Cart {
    private ArrayList<CartItem> items = new ArrayList<>();

    public void addProduct(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().getProductName().equals(product.getProductName())) {
                item.increase();
                return;
            }
        }
        items.add(new CartItem(product, 1));
    }

    public void removeProduct(CartItem item) {
        items.remove(item);
    }

    public double calculateTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public int getTotalItems() {
        int count = 0;
        for (CartItem item : items) count += item.getQuantity();
        return count;
    }

    public ArrayList<CartItem> getItems() { return items; }
    public boolean isEmpty() { return items.isEmpty(); }
    public void clear() { items.clear(); }
}