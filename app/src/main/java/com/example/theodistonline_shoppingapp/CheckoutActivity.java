package com.example.theodistonline_shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private EditText etName, etPhone, etLocation;
    private TextView tvSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etLocation = findViewById(R.id.etLocation);
        tvSummary = findViewById(R.id.tvSummary);
        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        // Pre-fill fields from logged-in user if available
        Customer current = CustomerStore.getCurrent();
        if (current != null) {
            if (current.getFullName() != null && !current.getFullName().isEmpty()) {
                etName.setText(current.getFullName());
            }
            if (current.getPhone() != null && !current.getPhone().isEmpty()) {
                etPhone.setText(current.getPhone());
            }
            if (current.getLocation() != null && !current.getLocation().isEmpty()) {
                etLocation.setText(current.getLocation());
            }
        }

        showOrderSummary();

        btnPlaceOrder.setOnClickListener(v -> placeOrder());
    }

    private void showOrderSummary() {
        Cart cart = CartManager.getCart();
        StringBuilder sb = new StringBuilder();
        for (CartItem item : cart.getItems()) {
            sb.append(item.getQuantity()).append(" x ")
                    .append(item.getProduct().getProductName())
                    .append("  -  K").append(String.format(Locale.getDefault(), "%.2f", item.getSubtotal()))
                    .append("\n");
        }
        sb.append("\nTOTAL: K").append(String.format(Locale.getDefault(), "%.2f", cart.calculateTotal()));
        tvSummary.setText(sb.toString());
    }

    private void placeOrder() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String location = etLocation.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Please enter your name");
            return;
        }
        if (phone.length() < 7 || !phone.matches("[0-9+ ]+")) {
            etPhone.setError("Enter a valid phone number");
            return;
        }
        if (location.isEmpty()) {
            etLocation.setError("Please enter your delivery location");
            return;
        }

        Order order = new Order(name, phone, location, CartManager.getCart());

        new AlertDialog.Builder(this)
                .setTitle("Order Confirmed (Prototype)")
                .setMessage(order.getSummary()
                        + "\n\nThis is a prototype. No real payment was processed.")
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> {
                    CartManager.getCart().clear();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                })
                .show();
    }
}
