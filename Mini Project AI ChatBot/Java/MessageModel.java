package com.example.aichatbot;

public class MessageModel {
    String message;
    String sender;

    public MessageModel(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() { return message; }
    public String getSender() { return sender; }
}
