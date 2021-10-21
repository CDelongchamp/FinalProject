package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class EditUserActivity extends AppCompatActivity {

    Button backButton;
    CheckBox isMemberBox;
    CheckBox isInstructorBox;
    TextView usernameTextBox;
    Spinner spinner;
    DB_Management myDB;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        backButton = findViewById(R.id.backButton2);
        isMemberBox = findViewById(R.id.isMemberBox);
        isInstructorBox = findViewById(R.id.isInstructorBox);
        usernameTextBox = findViewById(R.id.usernameTextBox);
        spinner = findViewById(R.id.userSpinner);

        loadSpinnerData();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        Object item = parent.getItemAtPosition(pos);

                        username = item.toString();
                        usernameTextBox.setText(username);

                        String[] roles = myDB.getUserRoles(username);

                        for(int i = 0;i<roles.length; i++){
                            if(roles[i] != null) {
                                if (roles[i].contains("2")) {
                                    isInstructorBox.setSelected(true);
                                }
                                if (roles[i].contains("3")) {
                                    isMemberBox.setSelected(true);
                                }
                            }
                        }




                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }

    public void fetchUserData(){

    }

    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
        myDB = new DB_Management(this );
        List<String> labels = myDB.getAllUsers();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
}