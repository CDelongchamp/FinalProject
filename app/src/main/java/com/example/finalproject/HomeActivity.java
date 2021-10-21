package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button selectAdmin;
    Button selectMember;
    Button selectInstructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        selectAdmin = (Button) findViewById(R.id.selectAdmin);
        selectMember = (Button) findViewById(R.id.selectMember);
        selectInstructor = (Button) findViewById(R.id.selectInstructor);

        selectMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MemberActivity.class);
                startActivity(intent);
            }
        });

        selectInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),InstructorActivity.class);
                startActivity(intent);
            }
        });

        selectAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AdminActivity.class);
                startActivity(intent);
            }
        });
    }



}