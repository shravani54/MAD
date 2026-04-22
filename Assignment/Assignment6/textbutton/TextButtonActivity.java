package com.example.assignment6.textbutton;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment6.R;

public class TextButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_button);

        TextView txt = findViewById(R.id.txt);

        txt.setOnClickListener(v ->
                Toast.makeText(this, "Text Clicked", Toast.LENGTH_SHORT).show());
    }
}