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
    DB_Management myDB;
    LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        myDB = new DB_Management(this );



        selectAdmin = (Button) findViewById(R.id.selectAdmin);
        selectInstructor = (Button) findViewById(R.id.selectInstructor);
        selectMember = (Button) findViewById(R.id.selectMember);


        selectAdmin.setEnabled(false);
        selectInstructor.setEnabled(false);
        selectMember.setEnabled(false);


        String username = LoginActivity.getUser();
        String[] role = myDB.getUserRoles(username);
        for(int i = 0; i < role.length ; i++){
            if(role[i] != null) {
                if (role[i].contains("1")) {
                    selectAdmin.setEnabled(true);
                }
                if (role[i].contains("2")) {
                    selectInstructor.setEnabled(true);
                }
                if (role[i].contains("3")) {
                    selectMember.setEnabled(true);
                }
            }
        }



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