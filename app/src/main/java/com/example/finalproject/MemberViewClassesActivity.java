package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MemberViewClassesActivity extends AppCompatActivity {

    DB_Management myDB;
    RecyclerView recyclerView;
    Button unenrollButton;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_view_classes);

        myDB = new DB_Management(this);
        recyclerView = findViewById(R.id.recyclerClassView);
        unenrollButton = findViewById(R.id.unenrollButton);

        username = LoginActivity.getUser();

        // TODO
        //

        unenrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areYouSureAlert();
            }
        });
    }

    private void areYouSureAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?")
                .setTitle("You are about to cancel the selected class.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO
                        // remove user from selected class
                        Toast.makeText(MemberViewClassesActivity.this, "Unenrolled form class successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                });
        builder.create();
        builder.show();
    }
}