package com.example.theodistonline_shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Product product = ProductCatalog.findByName(getIntent().getStringExtra("name"));
        if (product == null) {
            finish();
            return;
        }

        ((ImageView) findViewById(R.id.ivDetailProduct)).setImageResource(product.getImageResId());
        ((TextView) findViewById(R.id.tvDetailName)).setText(product.getProductName());
        // Polymorphism: each child class runs its own displayProduct()
        ((TextView) findViewById(R.id.tvDetailLabel)).setText(product.displayProduct());
        ((TextView) findViewById(R.id.tvDetailCategory)).setText("Category: " + product.getCategory());
        ((TextView) findViewById(R.id.tvDetailPrice))
                .setText("Price: K" + String.format(Locale.getDefault(), "%.2f", product.getPrice()));

        Button btnAdd = findViewById(R.id.btnDetailAdd);
        Button btnCart = findViewById(R.id.btnDetailCart);
        Button btnBack = findViewById(R.id.btnDetailBack);

        btnAdd.setOnClickListener(v -> {
            CartManager.getCart().addProduct(product);
            Toast.makeText(this, product.getProductName() + " added to cart",
                    Toast.LENGTH_SHORT).show();
        });
        btnCart.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
        btnBack.setOnClickListener(v -> finish());
    }
}
