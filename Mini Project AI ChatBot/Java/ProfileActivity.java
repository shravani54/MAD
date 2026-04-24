package com.example.aichatbot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    TextView nameText, emailText;
    Button logoutBtn;
    ListView chatDateList;

    DBHelper dbHelper;
    String email;

    ArrayList<String> dates;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameText = findViewById(R.id.nameText);
        emailText = findViewById(R.id.emailText);
        logoutBtn = findViewById(R.id.logoutBtn);
        chatDateList = findViewById(R.id.chatDateList);

        dbHelper = new DBHelper(this);

        SharedPreferences sp = getSharedPreferences("LoginSession", MODE_PRIVATE);
        email = sp.getString("userEmail", null);

        if (email == null) {
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
            return;
        }

        String name = dbHelper.getUserName(email);

        nameText.setText(name);
        emailText.setText(email);

        loadChatDates();

        chatDateList.setOnItemClickListener((parent, view, position, id) -> {
            String selectedDate = dates.get(position);

            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.putExtra("chatDate", selectedDate);
            startActivity(intent);
        });

        logoutBtn.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    void loadChatDates() {
        dates = new ArrayList<>();

        Cursor c = dbHelper.getChatDates(email);

        while (c.moveToNext()) {
            dates.add(c.getString(0));
        }

        c.close();

        if (dates.isEmpty()) {
            dates.add("No chat history yet");
        }

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                dates
        );

        chatDateList.setAdapter(adapter);
    }
}