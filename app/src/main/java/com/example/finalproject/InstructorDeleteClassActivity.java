package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InstructorDeleteClassActivity extends AppCompatActivity {

    Button backButton, classCancelButton;
    DB_Management myDB;
    String username;
    Spinner classSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_delete_class);

        backButton = findViewById(R.id.backButton7);
        classCancelButton = findViewById(R.id.classCancelButton);
        username = LoginActivity.getUser();
        classSpinner = findViewById(R.id.classSpinner3);

        loadSpinnerData();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        classCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelClass();
            }
        });

    }

    public void cancelClass() {
        int classID = Integer.parseInt(classSpinner.getSelectedItem().toString().split(" ")[1]);

        Boolean cancelClass = myDB.deleteClass(classID);

        if(cancelClass) {
            Toast.makeText(InstructorDeleteClassActivity.this, "Class was cancelled succesfully.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(InstructorDeleteClassActivity.this, "Class was not cancelled.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSpinnerData() {

        myDB = new DB_Management(this );
        List<String> temp = myDB.getAllClassesByInstructorName(username);
        List<String> labels = new ArrayList<>();

        for (String classInfo : temp) {
            List<String> splitClass = Arrays.asList(classInfo.split(" "));
            String shortenedLevel = splitClass.get(2).substring(0,3);
            labels.add("ID: "+splitClass.get(0)+" "+splitClass.get(1) +" "+shortenedLevel+" "+splitClass.get(3)+" at "+splitClass.get(4));
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        classSpinner.setAdapter(dataAdapter);

    }


}