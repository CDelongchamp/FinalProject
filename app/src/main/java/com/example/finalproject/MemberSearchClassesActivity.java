package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberSearchClassesActivity extends AppCompatActivity {

    DB_Management myDB;
    Button enrollButton;
    String username;
    String classId;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_search_classes);

        myDB = new DB_Management(this);
        enrollButton = findViewById(R.id.enrollButton);
        backButton = findViewById(R.id.backButton512);
        username = LoginActivity.getUser();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        enrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFull = checkCapacity();
                boolean isTimeConflicted = checkTimeConflict();
                if (!isFull && !isTimeConflicted) {
                    myDB.enrollInClass(username, classId);
                } else {
                    if (isFull && isTimeConflicted) {
                        Toast.makeText(MemberSearchClassesActivity.this,"The class is full and the time conflicts with another class.", Toast.LENGTH_SHORT);
                    } else if (isFull) {
                        Toast.makeText(MemberSearchClassesActivity.this,"The class is full.", Toast.LENGTH_SHORT);
                    } else if (isTimeConflicted) {
                        Toast.makeText(MemberSearchClassesActivity.this,"The time conflicts with another class.", Toast.LENGTH_SHORT);
                    } else {
                        Toast.makeText(MemberSearchClassesActivity.this,"Unable to enroll.", Toast.LENGTH_SHORT);
                    }
                }
            }
        });
    }

    private boolean checkCapacity() {
        return myDB.getFreeSpotsOfClass(classId) <= 0;
    }

    private boolean checkTimeConflict() {
        Date[] selectedClassTimes = myDB.getClassTimeByClassId(classId);
        List<String> enrolledClasses = myDB.getAllClassesByEnrolment(username);
        List<Date[]> enrolledClassesTimes = new ArrayList<>();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
//        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        for (String enrolledClassId : enrolledClasses) {
            enrolledClassesTimes.add(myDB.getClassTimeByClassId(enrolledClassId));
        }

        Date selectedStartTime = selectedClassTimes[0];
        Date selectedEndTime = selectedClassTimes[1];

        for (Date[] enrolledTimes : enrolledClassesTimes) {
            Date enrolledStartTime = enrolledTimes[0];
            Date enrolledEndTime = enrolledTimes[1];

            // a.start <= b.end
            boolean enStartBeforeSelEnd = enrolledStartTime.compareTo(selectedEndTime) <= 0;

            // b.start <= a.end
            boolean selStartBeforeEnEnd = selectedStartTime.compareTo(enrolledEndTime) <= 0;
            if (enStartBeforeSelEnd && selStartBeforeEnEnd) {
                return true;
            }
        }

        return false;
    }
}