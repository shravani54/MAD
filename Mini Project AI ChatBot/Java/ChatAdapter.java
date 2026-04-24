package com.example.aichatbot;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<MessageModel> list;
    Context context;
    TextToSpeech tts;

    public ChatAdapter(List<MessageModel> list, Context context) {
        this.list = list;
        this.context = context;

        tts = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getSender().equals("user") ? 1 : 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
            return new UserViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_bot, parent, false);
            return new BotViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel msg = list.get(position);

        if (holder instanceof UserViewHolder) {
            ((UserViewHolder) holder).text.setText(msg.getMessage());
        } else {
            BotViewHolder botHolder = (BotViewHolder) holder;
            botHolder.text.setText(msg.getMessage());

            botHolder.speakBtn.setOnClickListener(v -> {
                if (tts != null) {
                    tts.speak(msg.getMessage(), TextToSpeech.QUEUE_FLUSH, null, null);
                }
            });

            botHolder.copyBtn.setOnClickListener(v -> {
                ClipboardManager clipboard =
                        (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

                ClipData clip = ClipData.newPlainText("chat_message", msg.getMessage());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        UserViewHolder(View v) {
            super(v);
            text = v.findViewById(R.id.userText);
        }
    }

    static class BotViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView speakBtn, copyBtn;

        BotViewHolder(View v) {
            super(v);
            text = v.findViewById(R.id.botText);
            speakBtn = v.findViewById(R.id.speakMessageBtn);
            copyBtn = v.findViewById(R.id.copyMessageBtn);
        }
    }
}