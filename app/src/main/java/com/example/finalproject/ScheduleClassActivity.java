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
    Spinner fitnessTypeSpinner, difficultySpinner, dayOfWeekSpinner;
    EditText startTimeEdit, endTimeEdit, maximumCapacityNumber;
    DB_Management myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_class);

        myDB = new DB_Management(this);
        backButton = findViewById(R.id.backButton5);
        scheduleClass = findViewById(R.id.scheduleClassButton);
        fitnessTypeSpinner = findViewById(R.id.fitnessTypeSpinner);
        difficultySpinner = findViewById(R.id.difficultySpinner);
        dayOfWeekSpinner = findViewById(R.id.dayOfWeekSpinner);
        startTimeEdit = findViewById(R.id.startTimeEdit);
        endTimeEdit = findViewById(R.id.endTimeEdit);
        maximumCapacityNumber = findViewById(R.id.maximumCapacityNumber);

        loadFitnessSpinnerData();
        loadDifficultySpinnerData();
        loadDayOfWeekSpinnerData();

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
                dayOfWeek = dayOfWeekSpinner.getSelectedItem().toString();
                startTime = Integer.getInteger(startTimeEdit.getText().toString());
                endTime = Integer.getInteger(endTimeEdit.getText().toString());
                maximumCapacity = Integer.getInteger(maximumCapacityNumber.getText().toString());


                // incorrect field entry cases

                boolean isIncorrect = false;
                if (fitnessType.length() == 0) {
                    fitnessTypeSpinner.setBackgroundColor(Color.RED);
                    isIncorrect = true;
                }
                if (difficulty.length() == 0) {
                    difficultySpinner.setBackgroundColor(Color.RED);
                    isIncorrect = true;
                }
                if (dayOfWeek.length() == 0) {
                    dayOfWeekSpinner.setBackgroundColor(Color.RED);
                    isIncorrect = true;
                }
                if (startTime == 0) {
                    startTimeEdit.setHintTextColor(Color.RED);
                }
                if (endTime == 0) {
                    endTimeEdit.setHintTextColor(Color.RED);
                }
                if (maximumCapacity == 0) {
                    maximumCapacityNumber.setHintTextColor(Color.RED);
                }
                if (isIncorrect) {
                    Toast.makeText(ScheduleClassActivity.this, "Please fill in the fields above correctly.", Toast.LENGTH_SHORT).show();
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

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        difficultySpinner.setAdapter(dataAdapter);

    }
    private void loadDayOfWeekSpinnerData() {
        List<String> labels = new ArrayList<>();
        labels.add("Sunday");
        labels.add("Monday");
        labels.add("Tuesday");
        labels.add("Wednesday");
        labels.add("Thursday");
        labels.add("Friday");
        labels.add("Saturday");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        dayOfWeekSpinner.setAdapter(dataAdapter);

    }
}