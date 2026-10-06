package com.example.theodistonline_shoppingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private CheckBox cbRememberMe;
    private Button btnLogin;
    private ProgressBar progressBar;
    private ImageView ivTogglePassword;
    private boolean isPasswordVisible = false;

    private static final String PREF_NAME = "TheodistPrefs";
    private static final String KEY_REMEMBERED_USERNAME = "remembered_username";

    private final ActivityResultLauncher<Intent> signUpLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String registeredUser = result.getData().getStringExtra("registered_username");
                    if (registeredUser != null && !registeredUser.isEmpty()) {
                        if (etUsername != null) {
                            etUsername.setText(registeredUser);
                        }
                        if (etPassword != null) {
                            etPassword.setText("");
                            etPassword.requestFocus();
                        }
                        Toast.makeText(this, "Account created! Please enter your password to login.", Toast.LENGTH_LONG).show();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if user is already remembered / logged in
        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String rememberedUser = prefs.getString(KEY_REMEMBERED_USERNAME, "");
        if (!rememberedUser.isEmpty()) {
            Customer customer = CustomerStore.getCustomerByUsername(this, rememberedUser);
            if (customer != null) {
                // Skip login screen and go straight to MainActivity (Home page)
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                return;
            }
        }

        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);
        ivTogglePassword = findViewById(R.id.ivTogglePassword);
        TextView tvSignUpLink = findViewById(R.id.tvSignUpLink);

        if (!rememberedUser.isEmpty()) {
            etUsername.setText(rememberedUser);
            cbRememberMe.setChecked(true);
        }

        // Handle password visibility toggle
        ivTogglePassword.setOnClickListener(v -> togglePasswordVisibility());

        // Handle login click
        btnLogin.setOnClickListener(v -> attemptLogin());

        // Handle sign up link using ActivityResultLauncher
        tvSignUpLink.setOnClickListener(v -> {
            signUpLauncher.launch(new Intent(LoginActivity.this, SignUpActivity.class));
        });

        // Entrance animation: Fade and slide up
        View layoutRoot = findViewById(R.id.layoutRoot);
        if (layoutRoot != null) {
            layoutRoot.setAlpha(0f);
            layoutRoot.setTranslationY(50f);
            layoutRoot.animate().alpha(1f).translationY(0f).setDuration(600).start();
        }
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ivTogglePassword.setImageResource(android.R.drawable.ic_menu_view);
            isPasswordVisible = false;
        } else {
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ivTogglePassword.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
            isPasswordVisible = true;
        }
        etPassword.setSelection(etPassword.getText().length());
    }

    private void attemptLogin() {
        String usernameOrEmail = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (usernameOrEmail.isEmpty()) {
            etUsername.setError("Please enter your username or email");
            etUsername.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError("Please enter your password");
            etPassword.requestFocus();
            return;
        }

        // Show progress indicator
        btnLogin.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        // Authenticate via CustomerStore / UserDatabase
        Customer customer = CustomerStore.login(this, usernameOrEmail, password);

        // Simulate short delay for progress indicator experience
        progressBar.postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            btnLogin.setEnabled(true);

            if (customer == null) {
                Toast.makeText(LoginActivity.this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show();
            } else {
                // Save or clear remember me in SharedPreferences
                SharedPreferences.Editor editor = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
                if (cbRememberMe.isChecked()) {
                    editor.putString(KEY_REMEMBERED_USERNAME, customer.getUsername());
                } else {
                    editor.remove(KEY_REMEMBERED_USERNAME);
                }
                editor.apply();

                Toast.makeText(LoginActivity.this, "Welcome back, " + customer.getFullName() + "!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }, 600);
    }
}
