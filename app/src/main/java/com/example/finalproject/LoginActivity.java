package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username,password;
    Button Login2Button;
    DB_Management myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        Login2Button = (Button) findViewById(R.id.Login2Button);

        myDB = new DB_Management(this );

        Login2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Username or Password cannot be blank.", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean result = myDB.checkusernamePassword(user,pass);
                    if(result == true) {
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Login.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}