package com.example.assignment5.ImplicitIntent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment5.R;

public class ImplicitMainActivity extends AppCompatActivity {

    Button btnCall, btnGallery, btnWhatsapp, btnFlipkart, btnWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_implicit_main);

        btnCall = findViewById(R.id.btnCall);
        btnGallery = findViewById(R.id.btnGallery);
        btnWhatsapp = findViewById(R.id.btnWhatsapp);
        btnFlipkart = findViewById(R.id.btnFlipkart);
        btnWebsite = findViewById(R.id.btnWebsite);

        btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:9876543210"));
            startActivity(intent);
        });

        btnGallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivity(intent);
        });

        btnWhatsapp.setOnClickListener(v -> {
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
            if (intent != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "WhatsApp not installed", Toast.LENGTH_SHORT).show();
            }
        });

        btnFlipkart.setOnClickListener(v -> {
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.flipkart.android");
            if (intent != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "Flipkart not installed", Toast.LENGTH_SHORT).show();
            }
        });

        btnWebsite.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
            startActivity(intent);
        });
    }
}