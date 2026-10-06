package com.example.theodistonline_shoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFullName, etUsername, etEmail, etPhone, etLocation, etPassword, etConfirmPassword;
    private TextView tvPasswordStrength;
    private CheckBox cbTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etLocation = findViewById(R.id.etLocation);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        tvPasswordStrength = findViewById(R.id.tvPasswordStrength);
        cbTerms = findViewById(R.id.cbTerms);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        ImageView btnBack = findViewById(R.id.btnBack);
        TextView tvLoginLink = findViewById(R.id.tvLoginLink);

        btnBack.setOnClickListener(v -> finish());
        tvLoginLink.setOnClickListener(v -> finish());

        // Live password strength monitoring
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updatePasswordStrength(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnSignUp.setOnClickListener(v -> attemptSignUp());
    }

    private void updatePasswordStrength(String password) {
        if (password.length() < 6) {
            tvPasswordStrength.setText("Weak");
            tvPasswordStrength.setTextColor(getResources().getColor(R.color.coral));
        } else if (password.length() < 10 || !password.matches(".*[0-9].*") || !password.matches(".*[A-Za-z].*")) {
            tvPasswordStrength.setText("Medium");
            tvPasswordStrength.setTextColor(getResources().getColor(R.color.lavender));
        } else {
            tvPasswordStrength.setText("Strong");
            tvPasswordStrength.setTextColor(getResources().getColor(R.color.mint));
        }
    }

    private void attemptSignUp() {
        String fullName = etFullName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        // Validation
        if (fullName.isEmpty()) {
            etFullName.setError("Please enter your full name");
            etFullName.requestFocus();
            return;
        }
        if (username.length() < 3 || username.contains(" ")) {
            etUsername.setError("Username must be at least 3 characters and contain no spaces");
            etUsername.requestFocus();
            return;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            return;
        }
        if (phone.length() < 7) {
            etPhone.setError("Phone number must be at least 7 digits");
            etPhone.requestFocus();
            return;
        }
        if (location.isEmpty()) {
            etLocation.setError("Please enter your delivery location");
            etLocation.requestFocus();
            return;
        }
        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return;
        }
        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Please agree to the Terms & Conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        // Database uniqueness check
        UserDatabase db = new UserDatabase(this);
        if (db.usernameExists(username)) {
            etUsername.setError("Username is already taken");
            etUsername.requestFocus();
            return;
        }
        if (db.emailExists(email)) {
            etEmail.setError("Email is already registered");
            etEmail.requestFocus();
            return;
        }

        boolean success = db.registerUser(fullName, username, email, phone, location, password);
        if (success) {
            Toast.makeText(this, "Account created successfully! Please login.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.putExtra("registered_username", username);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
