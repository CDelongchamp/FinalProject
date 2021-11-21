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

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        SignUpButton = findViewById(R.id.SignUpButton);
        LogInButton = findViewById(R.id.LogInButton);

        checkBoxInstructor = findViewById(R.id.checkBoxInstructor);
        checkBoxMember = findViewById(R.id.checkBoxMember);


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
        });

        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    /** This method checks if the user name is valid , meaning only contains alphabets
     *
     * @param name takes in the String username anc checks if it is alphabetical
     * @return a boolean value, if it is true it means our password only contains alphabets if there are numbers or other characters our useername is invalid
     */
    public static boolean isValidName(String name){

        return name.matches("[a-zA-Z]+");
    }
    /** This method checks if the password is valid , meaning it does not contain any space
     *
     * @param password takes in the String password and checks if it contains any white space,
     * @return a boolean value, returning true means that it does not contain white space and can be used as a password
     */
    public static boolean isPasswordContainsEmptySpace(String password){
        return !password.matches(".*\\s+.*");
    }

}