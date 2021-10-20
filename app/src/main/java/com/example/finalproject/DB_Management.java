package com.example.finalproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DB_Management extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public DB_Management(Context context) {

        super(context,"Login.db", null,1);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("CREATE TABLE users(" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT)");

        myDB.execSQL("CREATE TABLE role_types(" +
                "role_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "role_name TEXT)");

        myDB.execSQL("CREATE TABLE class_types(" +
                "class_type TEXT PRIMARY KEY," +
                "description TEXT)");

        myDB.execSQL("CREATE TABLE class_difficulties(" +
                "difficulty_id TEXT PRIMARY KEY)");

        myDB.execSQL("CREATE TABLE classes(" +
                "class_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT," +
                "difficulty TEXT," +
                "start_time INTEGER," +
                "end_time INTEGER," +
                "capacity INTEGER," +
                "instructor interger," +
                "FOREIGN KEY(type) REFERENCES class_types(class_type)," +
                "FOREIGN KEY(difficulty) REFERENCES class_difficulties(difficulty_id)," +
                "FOREIGN KEY(instructor) REFERENCES users(user_id))");

        myDB.execSQL("CREATE TABLE roles(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id TEXT, " +
                "role_id TEXT," +
                "FOREIGN KEY(user_id) REFERENCES users(username)," +
                "FOREIGN KEY(role_id) REFERENCES role_types(role_id)) ");

        myDB.execSQL("CREATE TABLE class_enrolment(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id TEXT," +
                "class_id TEXT," +
                "FOREIGN KEY(user_id) REFERENCES users(username)," +
                "FOREIGN KEY(class_id) REFERENCES classes(class_id))");

        myDB.execSQL("INSERT INTO class_difficulties(difficulty_id) VALUES(\"easy\")");
        myDB.execSQL("INSERT INTO class_difficulties(difficulty_id) VALUES(\"medium\")");
        myDB.execSQL("INSERT INTO class_difficulties(difficulty_id) VALUES(\"hard\")");

        myDB.execSQL("INSERT INTO role_types(role_name) VALUES(\"Administrator\")");
        myDB.execSQL("INSERT INTO role_types(role_name) VALUES(\"Instructor\")");
        myDB.execSQL("INSERT INTO role_types(role_name) VALUES(\"Member\")");

        myDB.execSQL("INSERT INTO class_types(class_type, description) VALUES(\"Yoga\", \"Experience peace and relaxation!\")");
        myDB.execSQL("INSERT INTO class_types(class_type, description) VALUES(\"Kickboxing\", \"Boxing, but with kicks!\")");
        myDB.execSQL("INSERT INTO class_types(class_type, description) VALUES(\"Karate\", \"Half Art, Half Combat\")");
        myDB.execSQL("INSERT INTO class_types(class_type, description) VALUES(\"Mortal Combat\", \"Nothing like Immortal Combat\")");

        myDB.execSQL("INSERT INTO users(username, password) VALUES(\"admin\", \"admin123\")");

        myDB.execSQL("INSERT INTO roles(user_id, role_id) VALUES(\"admin\",\"1\")");



    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int oldVersion, int newVersion) {
        myDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = myDB.insert("users", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ?",new String[] {username});
        if(cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean checkusernamePassword(String username, String password) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ? and password = ?",new String[] {username,password});
        if(cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }



}
