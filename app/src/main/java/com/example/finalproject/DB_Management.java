package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;


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
     * @return Returns 0 if successful, 1 if the user exists, 2 if there was an error adding the instructor role, 3 if there was an error adding the member role, 4 if neither role was selected.
     */
    public int insertNewUser(String username, String password, boolean is_instructor, boolean is_member) {
        if(!is_instructor && !is_member)
            return 4;

        SQLiteDatabase myDB = this.getWritableDatabase();
        //String query = "INSERT INTO users(username,password) VALUES('" + username + "', '" + password + "')";
        String query = "";

        ContentValues insertValues = new ContentValues();
        insertValues.put("username",username);
        insertValues.put("password",password);
        long users = db.insert("users", null, insertValues);


        //Cursor cursor = db.rawQuery(query, null);
        //if(cursor.moveToFirst()){
        if(users > 0){
            // Good, no return.
        }else{
            return 1;
            // Good, no return.
        }



        if(is_instructor) {
            insertValues = new ContentValues();
            insertValues.put("user_id",username);
            insertValues.put("role_id",2 +"");
            long result = db.insert("roles", null, insertValues);

            if (result > 0) {
                // Good, no return.
            } else {
                return 2;
            }
        }
        if(is_member) {
            insertValues = new ContentValues();
            insertValues.put("user_id",username);
            insertValues.put("role_id",3+"");
            long result = db.insert("roles", null, insertValues);

            if (result > 0) {
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

        if(username.contains("admin")){
            return false;
        }

        SQLiteDatabase myDB = this.getWritableDatabase();
        String query = "DELETE FROM users WHERE username ='" + username + "'";

        int users = myDB.delete("users", "username ='" + username + "'", null);

        //Cursor cursor = db.rawQuery(query, null);

        if(checkUsername(username)){
            return false; // It was found. Did not delete.
        }else{
            return true;
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
        String query = "DELETE FROM roles WHERE username ='" + username + "' AND role_id = " + role_id;

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }else{
            cursor.close();
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
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }

    }

    public Boolean checkusernamePassword(String username, String password) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ? and password = ?",new String[] {username,password});
        if(cursor.getCount()>0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
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
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    /**
     * Method adds a Class Type to the Database
     * @param class_type the Type of class, like Karate
     * @param description The description
     * @return Returns true if it's been added.
     */
    public Boolean createClassType(String class_type, String description){
        SQLiteDatabase myDB = this.getWritableDatabase();

        ContentValues insertValues = new ContentValues();
        insertValues.put("class_type",class_type);
        insertValues.put("description",description);
        long insertionStatus = db.insert("class_types", null, insertValues);

        if(insertionStatus > 0  ){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method deletes the class type.
     * @param class_type the class type to be deleted.
     * @return returns true if deleted.
     */
    public Boolean deleteClassType(String class_type){
        SQLiteDatabase myDB = this.getWritableDatabase();
        int rowsDeleted = myDB.delete("class_types", "class_type ='" + class_type + "'", null);

        if(rowsDeleted > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Edits teh class type, should edit both the name and description.
     * @param old_class_type the class type to be updated.
     * @param new_class_type the new class type.
     * @param description the description to be updated.
     * @return returns true if successful.
     */

    public Boolean editClassType(String old_class_type, String new_class_type, String description){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("class_type", new_class_type);
        cv.put("description", description);


        int rowsUpdated = myDB.update("class_types", cv, "class_type =?", new String[]{old_class_type});

        if(rowsUpdated>0){
            return true;
        }else
            return false;

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
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    /**
     * Method updates a class, all fields except class_id can be updated.
     * @param class_id the class to be updated.
     * @param type
     * @param difficulty
     * @param start_time
     * @param end_time
     * @param capacity
     * @param instructor
     * @return returns true if success, false otherwise.
     */
    public Boolean updateClass(int class_id, String type, String difficulty, int start_time, int end_time, int capacity, int instructor){
        SQLiteDatabase myDB = this.getWritableDatabase();
        String query = "UPDATE classes" +
                " SET type = " + type + ", difficulty = " + difficulty + ", start_time = " + start_time + ", end_time = " + end_time + ", capacity = " + capacity + ", instructor = " + instructor +
                " WHERE class_id = " + class_id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    /**
     * Method returns a list of roles a specific user has.
     * @param username the user we are checking for roles.
     * @return a array of strings that contain the results.
     */
    @SuppressLint("Range")
    public String[] getUserRoles(String username){
        String[] results = new String[3];
        SQLiteDatabase myDB = this.getReadableDatabase();
        String query = "SELECT role_id FROM roles WHERE user_id = '" + username + "'";
        Cursor cursor = db.rawQuery(query, null);

        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                results[i] = cursor.getString(0);
                i++;
            } while (cursor.moveToNext());
        }
        cursor.close();

        return results;
    }

    /**
     * Gets a list of all users in the database.
     * @return returns a list of strings with usernames of those users. These are the primary keys.
     */
    public List<String> getAllUsers() {
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM users";

        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<String> getAllClassTypes() {
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM class_types";

        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
