package com.example.menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnOption, btnContext, btnPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOption = findViewById(R.id.btnOption);
        btnContext = findViewById(R.id.btnContext);
        btnPopup = findViewById(R.id.btnPopup);

        btnOption.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, OptionActivity.class);
            startActivity(intent);
        });

        btnContext.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContextAcctivity.class);
            startActivity(intent);
        });

        btnPopup.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PopupActivity.class);
            startActivity(intent);
        });
    }
}