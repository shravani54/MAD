package com.example.assignment6;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment6.alertDailogActivity.AlertDialogActivity;
import com.example.assignment6.buttons.ButtonsActivity;
import com.example.assignment6.checkbox.CheckBoxActivity;
import com.example.assignment6.radiobutton.RadioButtonActivity;
import com.example.assignment6.spinner.SpinnerActivity;
import com.example.assignment6.textbutton.TextButtonActivity;
import com.example.assignment6.togglebutton.ToggleButtonActivity;

public class MainActivity extends AppCompatActivity {

    Button btnAlert, btnButtons, btnCheckBox, btnRadio, btnSpinner, btnText, btnToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAlert = findViewById(R.id.btnAlert);
        btnButtons = findViewById(R.id.btnButtons);
        btnCheckBox = findViewById(R.id.btnCheckBox);
        btnRadio = findViewById(R.id.btnRadio);
        btnSpinner = findViewById(R.id.btnSpinner);
        btnText = findViewById(R.id.btnText);
        btnToggle = findViewById(R.id.btnToggle);

        btnAlert.setOnClickListener(v ->
                startActivity(new Intent(this, AlertDialogActivity.class)));

        btnButtons.setOnClickListener(v ->
                startActivity(new Intent(this, ButtonsActivity.class)));

        btnCheckBox.setOnClickListener(v ->
                startActivity(new Intent(this, CheckBoxActivity.class)));

        btnRadio.setOnClickListener(v ->
                startActivity(new Intent(this, RadioButtonActivity.class)));

        btnSpinner.setOnClickListener(v ->
                startActivity(new Intent(this, SpinnerActivity.class)));

        btnText.setOnClickListener(v ->
                startActivity(new Intent(this, TextButtonActivity.class)));

        btnToggle.setOnClickListener(v ->
                startActivity(new Intent(this, ToggleButtonActivity.class)));
    }
}