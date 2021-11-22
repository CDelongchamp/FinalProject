package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MemberActivity extends AppCompatActivity {

    DB_Management myDB;
    String username;
//    String[] role;
    String role;
    Button viewCurrentClassesButton , searchAllClassesButton;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        myDB = new DB_Management(this );
        username = LoginActivity.getUser();
        viewCurrentClassesButton = findViewById(R.id.viewCurrentClassesButton);
        searchAllClassesButton = findViewById(R.id.SearchAllClassesButton);
//        role = myDB.getUserRoles(username);

        if (HomeActivity.getRole()) {
            role = "member";
        } else {
            role = "instructor";
        }

        TextView textView = findViewById(R.id.welcomeMessage);
        textView.setText("Welcome " + username + "! You are logged in as " + role + ".");

//        Toast.makeText(MemberActivity.this, role[0], Toast.LENGTH_SHORT).show();

        viewCurrentClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MemberViewClassesActivity.class);
                startActivity(intent);
            }
        });

        searchAllClassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MemberSearchClassesActivity.class);
                startActivity(intent);
            }
        });

    }
}