package com.example.theodistonline_shoppingapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the user's wishlist items in memory.
 */
public class WishlistManager {
    private static final List<Product> wishlist = new ArrayList<>();

    public static List<Product> getWishlist() {
        return wishlist;
    }

    public static boolean addProduct(Product product) {
        if (!contains(product)) {
            wishlist.add(product);
            return true;
        }
        return false;
    }

    public static void removeProduct(Product product) {
        wishlist.removeIf(p -> p.getProductName().equalsIgnoreCase(product.getProductName()));
    }

    public static boolean contains(Product product) {
        for (Product p : wishlist) {
            if (p.getProductName().equalsIgnoreCase(product.getProductName())) {
                return true;
            }
        }
        return false;
    }
}
