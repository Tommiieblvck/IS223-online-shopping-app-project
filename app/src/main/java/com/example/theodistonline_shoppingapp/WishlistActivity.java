package com.example.theodistonline_shoppingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WishlistActivity extends AppCompatActivity {

    private LinearLayout wishlistItemsContainer;
    private TextView tvEmptyWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        wishlistItemsContainer = findViewById(R.id.wishlistItemsContainer);
        tvEmptyWishlist = findViewById(R.id.tvEmptyWishlist);
        Button btnContinueShopping = findViewById(R.id.btnContinueShopping);

        btnContinueShopping.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshWishlist();
    }

    private void refreshWishlist() {
        wishlistItemsContainer.removeAllViews();
        List<Product> wishlist = WishlistManager.getWishlist();

        if (wishlist.isEmpty()) {
            tvEmptyWishlist.setVisibility(View.VISIBLE);
        } else {
            tvEmptyWishlist.setVisibility(View.GONE);
            for (Product p : new ArrayList<>(wishlist)) {
                View row = LayoutInflater.from(this).inflate(R.layout.item_wishlist, wishlistItemsContainer, false);

                TextView tvName = row.findViewById(R.id.tvWishlistItemName);
                TextView tvPrice = row.findViewById(R.id.tvWishlistItemPrice);
                Button btnAddToCart = row.findViewById(R.id.btnAddToCart);
                Button btnRemove = row.findViewById(R.id.btnRemoveWishlist);

                tvName.setText(p.getProductName());
                tvPrice.setText(String.format(Locale.getDefault(), "K%.2f", p.getPrice()));

                btnAddToCart.setOnClickListener(v -> {
                    CartManager.getCart().addProduct(p);
                    Toast.makeText(this, p.getProductName() + " added to cart", Toast.LENGTH_SHORT).show();
                });

                btnRemove.setOnClickListener(v -> {
                    WishlistManager.removeProduct(p);
                    Toast.makeText(this, p.getProductName() + " removed from wishlist", Toast.LENGTH_SHORT).show();
                    refreshWishlist();
                });

                wishlistItemsContainer.addView(row);
            }
        }
    }
}
