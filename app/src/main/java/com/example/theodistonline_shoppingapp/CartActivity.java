package com.example.theodistonline_shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {

    private LinearLayout cartItemsContainer;
    private TextView tvTotal, tvEmpty;
    private Button btnCheckout, btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartItemsContainer = findViewById(R.id.cartItemsContainer);
        tvTotal = findViewById(R.id.tvTotal);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnCheckout = findViewById(R.id.btnCheckout);
        btnContinue = findViewById(R.id.btnContinue);

        btnCheckout.setOnClickListener(v -> {
            if (CartManager.getCart().isEmpty()) {
                Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(new Intent(this, CheckoutActivity.class));
            }
        });

        btnContinue.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshCart();
    }

    private void refreshCart() {
        Cart cart = CartManager.getCart();
        cartItemsContainer.removeAllViews();

        tvEmpty.setVisibility(cart.isEmpty() ? View.VISIBLE : View.GONE);

        for (CartItem item : new java.util.ArrayList<>(cart.getItems())) {
            View row = LayoutInflater.from(this)
                    .inflate(R.layout.item_cart, cartItemsContainer, false);

            TextView tvName = row.findViewById(R.id.tvItemName);
            TextView tvPrice = row.findViewById(R.id.tvItemPrice);
            TextView tvQty = row.findViewById(R.id.tvItemQty);
            Button btnMinus = row.findViewById(R.id.btnMinus);
            Button btnPlus = row.findViewById(R.id.btnPlus);
            Button btnRemove = row.findViewById(R.id.btnRemove);

            tvName.setText(item.getProduct().displayProduct());
            tvPrice.setText("Subtotal: K" + String.format("%.2f", item.getSubtotal()));
            tvQty.setText(String.valueOf(item.getQuantity()));

            btnPlus.setOnClickListener(v -> { item.increase(); refreshCart(); });
            btnMinus.setOnClickListener(v -> { item.decrease(); refreshCart(); });
            btnRemove.setOnClickListener(v -> { cart.removeProduct(item); refreshCart(); });

            cartItemsContainer.addView(row);
        }

        tvTotal.setText("Total: K" + String.format("%.2f", cart.calculateTotal()));
    }
}