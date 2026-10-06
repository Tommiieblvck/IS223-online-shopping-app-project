package com.example.theodistonline_shoppingapp;

import java.util.ArrayList;
import java.util.Random;

public class Order {
    private String orderNumber;
    private String customerName;
    private String phone;
    private String location;
    private ArrayList<CartItem> items;
    private double total;

    public Order(String customerName, String phone, String location, Cart cart) {
        this.orderNumber = "TH" + (1000 + new Random().nextInt(9000));
        this.customerName = customerName;
        this.phone = phone;
        this.location = location;
        this.items = new ArrayList<>(cart.getItems());
        this.total = cart.calculateTotal();
    }

    public String getOrderNumber() { return orderNumber; }
    public double getTotal() { return total; }

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order No: ").append(orderNumber).append("\n");
        sb.append("Name: ").append(customerName).append("\n");
        sb.append("Phone: ").append(phone).append("\n");
        sb.append("Delivery: ").append(location).append("\n\n");
        for (CartItem item : items) {
            sb.append(item.getQuantity()).append(" x ")
                    .append(item.getProduct().getProductName())
                    .append(" = K").append(String.format("%.2f", item.getSubtotal()))
                    .append("\n");
        }
        sb.append("\nTOTAL: K").append(String.format("%.2f", total));
        return sb.toString();
    }
}