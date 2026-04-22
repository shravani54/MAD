package com.example.ise;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import com.example.ise.ListActivity;

public class MainActivity extends AppCompatActivity {
    EditText etName, etAddress, etMobile, etAge;
    RadioGroup rgGender;
    Button btnSubmit;

    public static ArrayList<String> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etMobile = findViewById(R.id.etMobile);
        etAge = findViewById(R.id.etAge);
        rgGender = findViewById(R.id.rgGender);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> submitForm());
    }

    private void submitForm(){
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();

        int genderId = rgGender.getCheckedRadioButtonId();
        if (name.isEmpty() || mobile.isEmpty() ||
                age.isEmpty() || address.isEmpty() ||
                genderId == -1) {

            Toast.makeText(this,
                    "Fill all fields",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String user = name + " : " + mobile;
        userList.add(user);

        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);

        clearForm();
    }

    private void clearForm(){
        etName.setText("");
        etAddress.setText("");
        etAge.setText("");
        etMobile.setText("");
    }
}