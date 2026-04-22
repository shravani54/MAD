package com.example.assignment6.spinner;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment6.R;

public class SpinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        Spinner spinner = findViewById(R.id.spinner);
        Button btn = findViewById(R.id.btnSelect);

        String[] items = {"Java", "Python", "Android"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                items
        );

        spinner.setAdapter(adapter);

        btn.setOnClickListener(v ->
                Toast.makeText(this,
                        spinner.getSelectedItem().toString(),
                        Toast.LENGTH_SHORT).show());
    }
}