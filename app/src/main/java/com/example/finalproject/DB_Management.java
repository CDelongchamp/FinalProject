package com.example.finalproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.text.BoringLayout;

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
                "FOREIGN KEY(instructor) REFERENCES users(username))");

        myDB.execSQL("CREATE TABLE roles(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id TEXT, " +
                "role_id TEXT," +
                "FOREIGN KEY(user_id) REFERENCES users(username) ON DELETE CASCADE," +
                "FOREIGN KEY(role_id) REFERENCES role_types(role_id)) ");

        myDB.execSQL("CREATE TABLE class_enrolment(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id TEXT," +
                "class_id TEXT," +
                "FOREIGN KEY(user_id) REFERENCES users(username) ON DELETE CASCADE," +
                "FOREIGN KEY(class_id) REFERENCES classes(class_id) ON DELETE CASCADE)");

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
        // TODO: Figure out a better way of doing this.
    }

    public Boolean insertData(String username, String password) { // TODO: fix this so that it has a more accurate name.
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

    /**
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param is_instructor A boolean if the user wants to be an instructor
     * @param is_member A boolean if the user wants to be a member.
     * @return Returns 0 if successful, 1 if the user exists, 2 if there was an error adding the instructor role, 3 if there was an error adding the member role.
     */
    public int insertNewUser(String username, String password, boolean is_instructor, boolean is_member) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        String query = "INSERT INTO users(username,password) VALUES(" + username + ", " + password + ")";


        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            // Good, no return.
        }else{
            return 1;
        }
        if(is_instructor) {
            query = "INSERT INTO roles(user_id, role_id) VALUES(" + username + ", " + 2 + ")"; // XXX hard coded role.

            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                // Good, no return.
            } else {
                return 2;
            }
        }
        if(is_member) {
            query = "INSERT INTO roles(user_id, role_id) VALUES(" + username + ", " + 3 + ")"; // XXX hard coded role.

            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                // Good, no return.
            } else {
                return 3;
            }
        }

        return 0;


    }

    /** Method tells the database to delete user. Will also cascade delete that user from enrolled classes and any roles they have.
     *
     * @param username the user to delete from the database.
     * @return Returns true if successful, false otherwise.
     */
    public Boolean deleteUser(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        String query = "DELETE FROM users WHERE username =" + username;

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    /** Method takes in both the username and the role_id and deletes the corresponding pair in the database.
     *
     * @param username the username of the role to be deleted.
     * @param role_id the role of the user to be deleted.
     * @return returns true if successful, false otherwise.
     */
    public Boolean deleteRole(String username, int role_id){
        SQLiteDatabase myDB = this.getWritableDatabase();
        String query = "DELETE FROM roles WHERE username =" + username + " AND role_id = " + role_id;

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    /** Method deletes a users Member role from the role table. Helper method.
     *
     * @param username whos member role is to be deleted.
     * @return returns true if successful, false otherwise.
     */
    public Boolean deleteMemberRole(String username){
        return deleteRole(username, 3);
    }
    
    /** Method deletes a users Instructor role from the role table. Helper method.
     *
     * @param username whos Instructor role is to be deleted.
     * @return returns true if successful, false otherwise.
     */
    public Boolean deleteInstructorRole(String username){
        return deleteRole(username, 2);
    }

    public Boolean checkUsername(String username) {
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

    /** Method to add a class to the class table.
     *
     * @param type The type of class, like Yoga.
     * @param difficulty the difficulty of the class, must be one present in the class_difficulties table.
     * @param start_time the start time in UTC.
     * @param end_time the end time in UTC
     * @param capacity Capacity of the class.
     * @param instructor the username of the user who is instructing this class. This will technically allow a member to be an instructor.
     * @return Returns true if it has been added, false otherwise.
     */
    public Boolean createClass(String type, String difficulty, int start_time, int end_time, int capacity, int instructor){
        SQLiteDatabase myDB = this.getWritableDatabase();
        String query = "INSERT INTO classes (type,difficulty,start_time,end_time,capacity,instructor) VALUES(" + type + ", " + difficulty + ", " + start_time + ", " + end_time +", " + capacity + ", " + instructor + " )";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    /**
     *
     * @param class_id the class_id from the class table to be deleted.
     * @return returns true if deleted, otherwise returns false.
     */
    public Boolean deleteClass(int class_id){
        SQLiteDatabase myDB = this.getWritableDatabase();
        String query = "DELETE FROM classes WHERE class_id =" + class_id;

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }









}
