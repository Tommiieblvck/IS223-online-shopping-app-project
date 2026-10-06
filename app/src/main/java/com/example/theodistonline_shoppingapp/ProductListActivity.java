package com.example.theodistonline_shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ProductListActivity extends AppCompatActivity {

    private LinearLayout productContainer;
    private TextView tvTitle;
    private Button btnViewCart;
    private String category = "All";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        productContainer = findViewById(R.id.productContainer);
        tvTitle = findViewById(R.id.tvTitle);
        btnViewCart = findViewById(R.id.btnViewCart);

        String extra = getIntent().getStringExtra("category");
        if (extra != null) category = extra;

        findViewById(R.id.btnFilterAll).setOnClickListener(v -> setCategory("All"));
        findViewById(R.id.btnFilterStationary).setOnClickListener(v -> setCategory("Stationary"));
        findViewById(R.id.btnFilterElectronics).setOnClickListener(v -> setCategory("Electronics"));
        findViewById(R.id.btnFilterClothing).setOnClickListener(v -> setCategory("Clothing"));
        btnViewCart.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));

        showProducts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartButton();
    }

    private void setCategory(String newCategory) {
        category = newCategory;
        showProducts();
    }

    private void updateCartButton() {
        btnViewCart.setText("View Cart (" + CartManager.getCart().getTotalItems() + ")");
    }

    private void showProducts() {
        tvTitle.setText(category.equals("All") ? "All Products" : category + " Products");
        productContainer.removeAllViews();

        for (Product p : ProductCatalog.getByCategory(category)) {
            View row = LayoutInflater.from(this)
                    .inflate(R.layout.item_product, productContainer, false);

            ImageView ivProduct = row.findViewById(R.id.ivProduct);
            TextView tvName = row.findViewById(R.id.tvProductName);
            TextView tvCategory = row.findViewById(R.id.tvProductCategory);
            TextView tvPrice = row.findViewById(R.id.tvProductPrice);
            Button btnAdd = row.findViewById(R.id.btnAddToCart);

            ivProduct.setImageResource(p.getImageResId());
            tvName.setText(p.getProductName());
            tvCategory.setText(p.getCategory());
            tvPrice.setText("K" + String.format(Locale.getDefault(), "%.2f", p.getPrice()));

            btnAdd.setOnClickListener(v -> {
                CartManager.getCart().addProduct(p);
                Toast.makeText(this, p.getProductName() + " added to cart",
                        Toast.LENGTH_SHORT).show();
                updateCartButton();
            });

            row.setOnClickListener(v -> {
                Intent intent = new Intent(this, ProductDetailActivity.class);
                intent.putExtra("name", p.getProductName());
                startActivity(intent);
            });

            productContainer.addView(row);
        }
        updateCartButton();
    }
}
