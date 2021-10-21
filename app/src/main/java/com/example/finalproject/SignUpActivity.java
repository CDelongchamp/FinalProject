package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    EditText username,password;
    Button SignUpButton;
    Button LogInButton;
    DB_Management myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

        SignUpButton = (Button) findViewById(R.id.SignUpButton);
        LogInButton = (Button)findViewById(R.id.LogInButton);

        myDB = new DB_Management( this);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("") || pass.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Username or Password cannot be blank.", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean usercheckResult = myDB.checkUsername(user);
                    if(usercheckResult == false) {
                        Boolean regResult = myDB.insertData(user,pass);
                        if(regResult == true) {
                            Toast.makeText(SignUpActivity.this, "Profile Successfully made.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Sign Up was not successful.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(SignUpActivity.this, "Username is already Taken.", Toast.LENGTH_SHORT).show();
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