package com.example.assignment6.radiobutton;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment6.R;

public class RadioButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);

        RadioGroup group = findViewById(R.id.group);
        Button btn = findViewById(R.id.btnCheck);

        btn.setOnClickListener(v -> {
            int id = group.getCheckedRadioButtonId();

            if (id != -1) {
                RadioButton rb = findViewById(id);
                Toast.makeText(this, rb.getText(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Select one option", Toast.LENGTH_SHORT).show();
            }
        });
    }
}