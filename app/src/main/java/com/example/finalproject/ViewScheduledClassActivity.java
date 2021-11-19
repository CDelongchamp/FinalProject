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

import java.util.List;

public class ViewScheduledClassActivity extends AppCompatActivity {

    Button backButton;
    DB_Management myDB;
    MyRecyclerViewAdapter adapter;
    Spinner classTypeSpinner, instructorSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_scheduled_class);

        backButton = findViewById(R.id.backButton4);
        classTypeSpinner = findViewById(R.id.classNameSpinner);
        instructorSpinner = findViewById(R.id.instructorNameSpinner);

        loadClassTypeSpinnerData();
        loadInstructorSpinnerData();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.classListRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, getScheduledClassesList());
        recyclerView.setAdapter(adapter);
    }

    /**
     * Function to load the spinner data from SQLite database
     *
     * @return*/
    private List<String> getScheduledClassesList() {
        myDB = new DB_Management(this );
        List<String> labels = myDB.getAllScheduledClasses();
        //labels.add(0,"");

        return labels;
    }

    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private List<String> mData;
        private LayoutInflater mInflater;

        // data is passed into the constructor
        MyRecyclerViewAdapter(Context context, List<String> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        // inflates the row layout from xml when needed
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.class_recycler_view_row, parent, false);
            return new ViewHolder(view);
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String s = mData.get(position);
            holder.myTextView.setText(s);
        }

        // total number of rows
        @Override
        public int getItemCount() {
            return mData.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView myTextView;

            ViewHolder(View itemView) {
                super(itemView);
                myTextView = itemView.findViewById(R.id.classType);

            }
        }

    }

    private void loadClassTypeSpinnerData() {
        myDB = new DB_Management(this );
        List<String> labels = myDB.getAllClassTypes();
        labels.add(0,"Class Type");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        classTypeSpinner.setAdapter(dataAdapter);
    }

    private void loadInstructorSpinnerData() {
        myDB = new DB_Management(this );
        List<String> labels = myDB.getAllInstructors();
        labels.add(0,"Instructors");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        instructorSpinner.setAdapter(dataAdapter);
    }

}