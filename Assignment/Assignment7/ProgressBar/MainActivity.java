package com.example.assignment7;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ProgressBar pb;
    Button btn;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        pb = findViewById(R.id.progressbar);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(v -> {
            pb.setProgress(50); // simple progress
            Toast.makeText(this, "Loading 50%", Toast.LENGTH_SHORT).show();
        });
    }
}