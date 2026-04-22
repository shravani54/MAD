package com.example.assignment6.checkbox;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment6.R;

public class CheckBoxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox);

        CheckBox c1 = findViewById(R.id.c1);
        Button btn = findViewById(R.id.btnShow);

        btn.setOnClickListener(v -> {
            if (c1.isChecked()) {
                Toast.makeText(this, "Checked", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Not Checked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}