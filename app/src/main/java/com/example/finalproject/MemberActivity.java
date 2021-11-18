package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MemberActivity extends AppCompatActivity {

    DB_Management myDB;
    String username;
//    String[] role;
    String role;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        myDB = new DB_Management(this );
        username = LoginActivity.getUser();
//        role = myDB.getUserRoles(username);

        if (HomeActivity.getRole()) {
            role = "member";
        } else {
            role = "instructor";
        }

        TextView textView = findViewById(R.id.welcomeMessage);
        textView.setText("Welcome " + username + "! You are logged in as " + role + ".");

//        Toast.makeText(MemberActivity.this, role[0], Toast.LENGTH_SHORT).show();

    }
}