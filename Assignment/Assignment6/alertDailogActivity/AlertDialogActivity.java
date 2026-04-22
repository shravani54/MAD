package com.example.assignment6.alertDailogActivity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment6.R;

public class AlertDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);

        Button btn = findViewById(R.id.btnAlert);

        btn.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Exit")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", (d, w) -> finish())
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}