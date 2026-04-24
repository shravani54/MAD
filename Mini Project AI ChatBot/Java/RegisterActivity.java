package com.example.aichatbot;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText nameEt, emailEt, passwordEt;
    Button registerBtn;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameEt = findViewById(R.id.nameEditText);
        emailEt = findViewById(R.id.emailEditText);
        passwordEt = findViewById(R.id.passwordEditText);
        registerBtn = findViewById(R.id.registerBtn);

        dbHelper = new DBHelper(this);

        registerBtn.setOnClickListener(v -> {
            String name = nameEt.getText().toString().trim();
            String email = emailEt.getText().toString().trim();
            String password = passwordEt.getText().toString().trim();

            if (name.isEmpty()) {
                nameEt.setError("Enter your name");
                nameEt.requestFocus();
                return;
            }

            if (email.isEmpty()) {
                emailEt.setError("Enter email");
                emailEt.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEt.setError("Enter valid email");
                emailEt.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                passwordEt.setError("Enter password");
                passwordEt.requestFocus();
                return;
            }

            if (password.length() < 6) {
                passwordEt.setError("Password must be at least 6 characters");
                passwordEt.requestFocus();
                return;
            }

            if (dbHelper.userExists(email)) {
                Toast.makeText(this, "Email already registered", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = dbHelper.registerUser(name, email, password);

            if (success) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}