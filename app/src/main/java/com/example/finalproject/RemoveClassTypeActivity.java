package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class RemoveClassTypeActivity extends AppCompatActivity {

    Button backButton;
    Button classTypeDeleteButton;
    Spinner classTypeDeleteSpinner;
    DB_Management myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_class_type);

        backButton = findViewById(R.id.backButton);
        classTypeDeleteButton = findViewById(R.id.classTypeDeleteButton);
        classTypeDeleteSpinner = findViewById(R.id.classDeleteSpinner);

        loadSpinnerData();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        classTypeDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void loadSpinnerData() {

    }

}
