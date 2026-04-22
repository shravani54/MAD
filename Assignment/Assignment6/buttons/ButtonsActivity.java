package com.example.assignment6.buttons;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment6.R;

public class ButtonsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);

        Button btn = findViewById(R.id.b1);

        btn.setOnClickListener(v ->
                Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show());
    }
}