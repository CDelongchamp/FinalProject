package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class EditClassActivity extends AppCompatActivity {

    EditText newName, newDescription;
    Button backButton;
    Button addChangesButton;
    Spinner spinner;
    DB_Management myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);

        newName = findViewById(R.id.newNameEditText);
        newDescription = findViewById(R.id.newDescriptionEditText);
        backButton = findViewById(R.id.backButton);
        addChangesButton = findViewById(R.id.addChangesButton);
        spinner = findViewById(R.id.classSpinner);
        myDB = new DB_Management(this);

        loadSpinnerData();

        addChangesButton.setOnClickListener(new View.OnClickListener() {

            String name = newName.getText().toString();
            String description = newDescription.getText().toString();

            public void onClick(View v) {
                String oldClassType = spinner.getSelectedItem().toString();

                Boolean editClassType = myDB.editClassType(oldClassType,name,description);

                if(editClassType) {
                    Toast.makeText(EditClassActivity.this, "Changes were successful.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditClassActivity.this, "Could not save changes.", Toast.LENGTH_SHORT).show();
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


    private void loadSpinnerData() {
        List<String> labels = myDB.getAllClassTypes();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
}