package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    Button SignUpButton;
    Button LogInButton;
    DB_Management myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                if(user.equals("") || pass.equals(null)) {
                    Toast.makeText(MainActivity.this, "Username or Password cannot be blank.", Toast.LENGTH_SHORT).show();
                } else {

                }

            }
        });

    }
}