package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ScheduleClassActivity extends AppCompatActivity {

    Button backButton, scheduleClass;
    Spinner fitnessTypeSpinner, difficultySpinner;
    EditText maximumCapacityNumber;
    DB_Management myDB;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_class);

        myDB = new DB_Management(this);
        backButton = findViewById(R.id.backButton5);
        scheduleClass = findViewById(R.id.scheduleClassButton);
        fitnessTypeSpinner = findViewById(R.id.fitnessTypeSpinner);
        difficultySpinner = findViewById(R.id.difficultySpinner);
        maximumCapacityNumber = findViewById(R.id.maximumCapacityNumber);
        username = LoginActivity.getUser();

        loadFitnessSpinnerData();
        loadDifficultySpinnerData();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        scheduleClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fitnessType, difficulty, dayOfWeek;
                int startTime, endTime, maximumCapacity;

                fitnessType = fitnessTypeSpinner.getSelectedItem().toString();
                difficulty = difficultySpinner.getSelectedItem().toString();
                maximumCapacity = Integer.getInteger(maximumCapacityNumber.getText().toString());

                boolean isCreated = myDB.createClass(fitnessType,difficulty,0,0,maximumCapacity,username);
                // incorrect field entry cases
                if (!isCreated) {

                    if (fitnessType.length() == 0) {
                        fitnessTypeSpinner.setBackgroundColor(Color.RED);
                    }
                    if (difficulty.length() == 0) {
                        difficultySpinner.setBackgroundColor(Color.RED);
                    }
                    if (maximumCapacity == 0) {
                        maximumCapacityNumber.setHintTextColor(Color.RED);
                    }
                    Toast.makeText(ScheduleClassActivity.this, "Please fill in the fields above correctly.", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });
    }

    private void loadFitnessSpinnerData() {
        myDB = new DB_Management(this );
        List<String> labels = myDB.getAllClassTypes();
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        fitnessTypeSpinner.setAdapter(dataAdapter);
    }

    private void loadDifficultySpinnerData() {
        List<String> labels = new ArrayList<>();
        labels.add("Beginner");
        labels.add("Intermediate");
        labels.add("Advanced");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        difficultySpinner.setAdapter(dataAdapter);
    }
}