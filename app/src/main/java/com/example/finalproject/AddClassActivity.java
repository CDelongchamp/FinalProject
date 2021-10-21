package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddClassActivity extends AppCompatActivity {

    EditText classType, classDescription;
    Button backButton;
    Button addNewClassButton;
    DB_Management myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        backButton = findViewById(R.id.backButton);
        addNewClassButton = findViewById(R.id.addNewClassButton);
        classType = findViewById(R.id.classTypeEditText);
        classDescription = findViewById(R.id.classDescriptionEditText);

        myDB = new DB_Management(this);

        addNewClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = classType.getText().toString();
                String description = classDescription.getText().toString();

                if (myDB.createClassType(type, description)) {
                    Toast.makeText(AddClassActivity.this, "Class type added successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddClassActivity.this, "Could not create class type.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}