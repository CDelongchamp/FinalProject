package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class MemberActivity extends AppCompatActivity {

    DB_Management myDB;
    LoginActivity loginActivity;
    String username;
    String[] role;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        myDB = new DB_Management(this );
        username = LoginActivity.getUser();
        role = myDB.getUserRoles(username);

        TextView textView = (TextView) findViewById(R.id.welcomeMessage);
        textView.setText("Welcome " + username + "! You are logged in as " + role[0]);
    }
}