package com.example.aichatbot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "ChatBotDB", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT UNIQUE, password TEXT)");

        db.execSQL("CREATE TABLE chats(id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, chatDate TEXT, message TEXT, sender TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS chats");
        onCreate(db);
    }

    public boolean registerUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("email", email);
        cv.put("password", password);

        long result = db.insert("users", null, cv);
        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE email=? AND password=?",
                new String[]{email, password});
        boolean exists = c.moveToFirst();
        c.close();
        return exists;
    }

    public String getUserName(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM users WHERE email=?", new String[]{email});

        String name = "User";
        if (c.moveToFirst()) {
            name = c.getString(0);
        }

        c.close();
        return name;
    }

    public void insertChat(String email, String chatDate, String message, String sender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("email", email);
        cv.put("chatDate", chatDate);
        cv.put("message", message);
        cv.put("sender", sender);

        db.insert("chats", null, cv);
    }

    public Cursor getChats(String email, String chatDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM chats WHERE email=? AND chatDate=?",
                new String[]{email, chatDate}
        );
    }

    public Cursor getChatDates(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT DISTINCT chatDate FROM chats WHERE email=? ORDER BY chatDate DESC",
                new String[]{email}
        );
    }

    public boolean userExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT * FROM users WHERE email=?",
                new String[]{email}
        );

        boolean exists = c.moveToFirst();
        c.close();

        return exists;
    }
}