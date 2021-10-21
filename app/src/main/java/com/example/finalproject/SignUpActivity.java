package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    EditText username,password;
    Button SignUpButton;
    Button LogInButton;
    DB_Management myDB;
    CheckBox checkBoxInstructor;
    CheckBox checkBoxMember;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

        SignUpButton = (Button) findViewById(R.id.SignUpButton);
        LogInButton = (Button)findViewById(R.id.LogInButton);

        checkBoxInstructor = (CheckBox) findViewById(R.id.checkBoxInstructor);
        checkBoxMember = (CheckBox) findViewById(R.id.checkBoxMember);


        myDB = new DB_Management( this);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                Boolean is_member = checkBoxMember.isChecked();
                Boolean is_instructor = checkBoxInstructor.isChecked();


                if(user.equals("") || pass.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Username or Password cannot be blank.", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean usercheckResult = myDB.checkUsername(user);
                    if(usercheckResult == false) {

                        int regResult = myDB.insertNewUser(user,pass,is_instructor,is_member);

                        switch (regResult) {

                            case 0:
                                Toast.makeText(SignUpActivity.this, "Profile Successfully made.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                                break;

                            case 1:
                                Toast.makeText(SignUpActivity.this, "Username is already Taken.", Toast.LENGTH_SHORT).show();
                                break;

                            case 2:
                                Toast.makeText(SignUpActivity.this, "Member Sign Up was not successful.", Toast.LENGTH_SHORT).show();
                                break;

                            case 3:
                                Toast.makeText(SignUpActivity.this, "Instructor Sign Up was not successful.", Toast.LENGTH_SHORT).show();
                                break;

                            case 4:
                                Toast.makeText(SignUpActivity.this, "Either member or instructor needs to be selected.", Toast.LENGTH_SHORT).show();
                                break;




                        }

                    }

                }

            }
        });

        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}