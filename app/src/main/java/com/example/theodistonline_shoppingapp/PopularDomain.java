package com.example.theodistonline_shoppingapp;

public class PopularDomain {
    private String title;
    private double price;
    private double rating;
    private String category;
    private int imageResource;

    public PopularDomain(String title, double price, double rating, String category, int imageResource) {
        this.title = title;
        this.price = price;
        this.rating = rating;
        this.category = category;
        this.imageResource = imageResource;
    }

    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public double getRating() { return rating; }
    public String getCategory() { return category; }
    public int getImageResource() { return imageResource; }
}
