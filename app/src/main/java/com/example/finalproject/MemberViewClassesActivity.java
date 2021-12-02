package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MemberViewClassesActivity extends AppCompatActivity {

    DB_Management myDB;
//    RecyclerView recyclerView;
//    MyRecyclerViewAdapter adapter;
    Button unenrollButton;
    String username;
//    List<String> data;
    Spinner enrolledClassSpinner;
    EditText commentEdit;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_view_classes);

        myDB = new DB_Management(this);
//        recyclerView = findViewById(R.id.recyclerClassView);
        unenrollButton = findViewById(R.id.unenrollButton);
        enrolledClassSpinner = findViewById(R.id.enrolledClassSpinner);
        commentEdit = findViewById(R.id.classCommentEditText);
        backButton = findViewById(R.id.backButton99);

        username = LoginActivity.getUser();
        loadEnrolledClassSpinnerData();

//        RecyclerView recyclerView = findViewById(R.id.recyclerClassView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new MyRecyclerViewAdapter(this, data);
//        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        unenrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areYouSureAlert();
            }
        });

    }

    private void processComment() {
        // we don't care about their comments B)

        String comment = commentEdit.getText().toString();
        comment = "";
        commentEdit.setText(comment);
    }

    private void areYouSureAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure?")
                .setTitle("You are about to cancel the selected class.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String selectedItem = enrolledClassSpinner.getSelectedItem().toString();
                        int classId = Integer.parseInt(selectedItem.split(" ")[0]);
                        myDB.unenrolUser(username, classId);
                        processComment();
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

    private void noClassAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("")
                .setTitle("You are currently enrolled in 0 classes.")
                .setPositiveButton("Go back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("Enroll to a class", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        Intent intent = new Intent(getApplicationContext(),MemberSearchClassesActivity.class);
                        startActivity(intent);
                    }
                });
        builder.create();
        builder.show();
    }

    private void loadEnrolledClassSpinnerData() {

        List<String> enrolledClasses = myDB.getAllClassesByEnrolment(username);
        List<String> labels = new ArrayList<>();

        if (enrolledClasses.size() == 0) {
            noClassAlert();
            return;
        }

        for (String s : enrolledClasses) {
            labels.add(s+" "+myDB.getClassByClassId(s));
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        enrolledClassSpinner.setAdapter(dataAdapter);
    }

//    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
//
//        private List<String> mData;
//        private LayoutInflater mInflater;
//
//        // data is passed into the constructor
//        MyRecyclerViewAdapter(Context context, List<String> data) {
//            this.mInflater = LayoutInflater.from(context);
//            this.mData = data;
//        }
//
//        // inflates the row layout from xml when needed
//        @Override
//        public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            view = mInflater.inflate(R.layout.class_recycler_view_row, parent, false);
//            return new MyRecyclerViewAdapter.ViewHolder(view);
//        }
//
//        // binds the data to the TextView in each row
//        public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
//            String s = mData.get(position);
//            holder.myTextView.setText(s);
//        }
//
//        // total number of rows
//        @Override
//        public int getItemCount() {
//            return mData.size();
//        }
//
//        // stores and recycles views as they are scrolled off screen
//        public class ViewHolder extends RecyclerView.ViewHolder {
//            TextView myTextView;
//
//            ViewHolder(View itemView) {
//                super(itemView);
//                myTextView = itemView.findViewById(R.id.classType);
//
//            }
//        }
//
//    }
}