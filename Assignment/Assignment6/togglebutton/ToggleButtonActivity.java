package com.example.assignment6.togglebutton;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment6.R;

public class ToggleButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle);

        ToggleButton toggle = findViewById(R.id.toggle);
        Button btn = findViewById(R.id.btnCheck);

        btn.setOnClickListener(v -> {
            if (toggle.isChecked()) {
                Toast.makeText(this, "ON", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "OFF", Toast.LENGTH_SHORT).show();
            }
        });
    }
}