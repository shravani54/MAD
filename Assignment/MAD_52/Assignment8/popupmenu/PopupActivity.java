package com.example.menu;

import android.os.Bundle;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PopupActivity extends AppCompatActivity {

    Button btnShowPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        btnShowPopup = findViewById(R.id.btnShowPopup);

        btnShowPopup.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(PopupActivity.this, btnShowPopup);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();

                if (id == R.id.menu_share) {
                    Toast.makeText(PopupActivity.this, "Share clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.menu_update) {
                    Toast.makeText(PopupActivity.this, "Update clicked", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.menu_delete) {
                    Toast.makeText(PopupActivity.this, "Delete clicked", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });

            popupMenu.show();
        });
    }
}