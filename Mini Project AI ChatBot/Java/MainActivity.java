package com.example.aichatbot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editText;
    ImageView sendBtn, micBtn, profileBtn;
    TextView titleText;

    List<MessageModel> list;
    ChatAdapter adapter;

    DBHelper dbHelper;
    ApiService apiService;

    String userEmail;
    String chatDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        );

        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.editText);
        sendBtn = findViewById(R.id.sendBtn);
        micBtn = findViewById(R.id.micBtn);
        profileBtn = findViewById(R.id.profileBtn);
        titleText = findViewById(R.id.titleText);

        SharedPreferences sp = getSharedPreferences("LoginSession", MODE_PRIVATE);
        userEmail = sp.getString("userEmail", null);

        if (userEmail == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        chatDate = getIntent().getStringExtra("chatDate");

        if (chatDate == null) {
            chatDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    .format(new Date());
        }

        titleText.setText("AI Chatbot - " + chatDate);

        dbHelper = new DBHelper(this);
        apiService = ApiClient.getClient().create(ApiService.class);

        list = new ArrayList<>();
        adapter = new ChatAdapter(list, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadChatHistory();

        sendBtn.setOnClickListener(v -> {
            String msg = editText.getText().toString().trim();

            if (!msg.isEmpty()) {
                addToChat(msg, "user");
                editText.setText("");
                callAPI(msg);
            }
        });

        micBtn.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            );
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

            startActivityForResult(intent, 1);
        });

        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    void loadChatHistory() {
        Cursor c = dbHelper.getChats(userEmail, chatDate);

        while (c.moveToNext()) {
            String message = c.getString(3);
            String sender = c.getString(4);
            list.add(new MessageModel(message, sender));
        }

        c.close();
        adapter.notifyDataSetChanged();

        if (!list.isEmpty()) {
            recyclerView.scrollToPosition(list.size() - 1);
        }
    }

    void addToChat(String message, String sender) {
        list.add(new MessageModel(message, sender));
        dbHelper.insertChat(userEmail, chatDate, message, sender);
        adapter.notifyItemInserted(list.size() - 1);
        recyclerView.scrollToPosition(list.size() - 1);
    }

    void callAPI(String userMsg) {
        try {
            JSONObject json = new JSONObject();

            json.put("model", "openrouter/free");

            JSONArray messages = new JSONArray();

            JSONObject systemMsg = new JSONObject();
            systemMsg.put("role", "system");
            systemMsg.put("content", "You are a helpful AI chatbot assistant.");

            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", userMsg);

            messages.put(systemMsg);
            messages.put(userMessage);

            json.put("messages", messages);

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.parse("application/json")
            );

            Call<ResponseBody> call = apiService.getResponse(
                    "Bearer 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",
                    body
            );

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String res;

                        if (response.isSuccessful() && response.body() != null) {
                            res = response.body().string();
                        } else {
                            res = response.errorBody() != null
                                    ? response.errorBody().string()
                                    : "";
                        }

                        if (res == null || res.trim().isEmpty()) {
                            fallback(userMsg);
                            return;
                        }

                        JSONObject obj = new JSONObject(res);

                        if (obj.has("choices")) {
                            String reply = obj.getJSONArray("choices")
                                    .getJSONObject(0)
                                    .getJSONObject("message")
                                    .getString("content")
                                    .trim();

                            addToChat(reply, "bot");

                        } else if (obj.has("error")) {
                            String errorMsg = obj.getJSONObject("error")
                                    .optString("message", "Unknown API error");

                            addToChat("API Error: " + errorMsg, "bot");

                        } else {
                            fallback(userMsg);
                        }

                    } catch (Exception e) {
                        addToChat("Parsing Error: " + e.getMessage(), "bot");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    addToChat("Network Error: " + t.getMessage(), "bot");
                }
            });

        } catch (Exception e) {
            addToChat("Error: " + e.getMessage(), "bot");
        }
    }

    void fallback(String msg) {
        String reply = fallbackReply(msg);
        addToChat(reply, "bot");
    }

    String fallbackReply(String msg) {
        msg = msg.toLowerCase();

        if (msg.contains("hello") || msg.contains("hi")) {
            return "Hello! How can I help you today?";
        }

        if (msg.contains("time")) {
            return "Current time is " + DateFormat.getTimeInstance().format(new Date());
        }

        if (msg.contains("date")) {
            return "Today's date is " + DateFormat.getDateInstance().format(new Date());
        }

        if (msg.contains("java")) {
            return "Java is a popular programming language used for Android development.";
        }

        if (msg.contains("android")) {
            return "Android is a mobile operating system used for mobile applications.";
        }

        return "I could not connect to the AI server. Please try again.";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
            );

            if (result != null && !result.isEmpty()) {
                editText.setText(result.get(0));
                editText.setSelection(editText.getText().length());
            }
        }
    }
}