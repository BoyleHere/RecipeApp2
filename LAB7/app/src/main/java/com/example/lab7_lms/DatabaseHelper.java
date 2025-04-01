package com.example.lab7_lms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "students";
    private static final String COLUMN_ID = "roll_number";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MARKS = "marks";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " VARCHAR PRIMARY KEY, " +
                COLUMN_NAME + " VARCHAR, " +
                COLUMN_MARKS + " VARCHAR);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addStudent(String rollNumber, String name, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, rollNumber);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_MARKS, marks);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    public boolean updateStudent(String rollNumber, String name, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_MARKS, marks);
        int rowsUpdated = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{rollNumber});
        db.close();
        return rowsUpdated > 0;
    }

    public boolean deleteStudent(String rollNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{rollNumber});
        db.close();
        return rowsDeleted > 0;
    }

    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
