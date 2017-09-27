package com.example.android.lad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by user on 16/09/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "exercisesManager";
    private static final String TABLE_EXERCISES = "exercises";
    private static final String KEY_ID = "id";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_DATE = "date";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_BODY_PARTS = "bodyparts";
    private static final String KEY_BODY_FAT_PERCENTAGE = "bodyfatpercentage";
    private static final String KEY_PHOTO_PATH = "photopath";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXERCISES_TABLE = "CREATE TABLE " + TABLE_EXERCISES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_DURATION + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_WEIGHT + " TEXT,"
                + KEY_BODY_PARTS + " TEXT,"
                + KEY_BODY_FAT_PERCENTAGE + " TEXT,"
                + KEY_PHOTO_PATH + " TEXT"
                + ")";
        db.execSQL(CREATE_EXERCISES_TABLE);
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        // Create table again
        onCreate(db);
    }
    // Adding new student
    void addRecord(ExerciseRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DURATION, record.getmDuration()); // Contact Name
        values.put(KEY_DATE, record.getmDate());
        values.put(KEY_WEIGHT, record.getmWeight()); // Contact Phone
        values.put(KEY_BODY_PARTS, record.getmBodyParts());
        values.put(KEY_BODY_FAT_PERCENTAGE, record.getmBodyFatPercentage());
        values.put(KEY_PHOTO_PATH, record.getmPhotoPath());
        // Inserting Row
        db.insert(TABLE_EXERCISES, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
    // Getting single student
    ExerciseRecord getRecord(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EXERCISES, new String[] { KEY_ID,
                        KEY_DURATION, KEY_DATE, KEY_WEIGHT, KEY_BODY_PARTS, KEY_BODY_FAT_PERCENTAGE, KEY_PHOTO_PATH}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        ExerciseRecord record = new ExerciseRecord(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        // return student
        return record;
    }
    // Getting All Contacts
    public ArrayList<ExerciseRecord> getAllRecords() {
        ArrayList<ExerciseRecord> recordList = new ArrayList<ExerciseRecord>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_EXERCISES + " ORDER BY ID DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ExerciseRecord record = new ExerciseRecord();
                record.setID(Integer.parseInt(cursor.getString(0)));
                record.setmDuration(cursor.getString(1));
                record.setmDate(cursor.getString(2));
                record.setmWeight(cursor.getString(3));
                record.setmBodyParts(cursor.getString(4));
                record.setmBodyFatPercentage(cursor.getString(5));
                record.setmPhotoPath(cursor.getString(6));
                // Adding student to list
                recordList.add(record);
            } while (cursor.moveToNext());
        }

        Collections.sort(recordList, new Comparator<ExerciseRecord>() {
            @Override
            public int compare(ExerciseRecord e1, ExerciseRecord e2) {
                String dateString1 = e1.getmDate();
                String dateString2 = e2.getmDate();
                SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = dateFormater.parse(dateString1);
                    date2 = dateFormater.parse(dateString2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int compareResult = 0;
                if (date1.after(date2)) {
                    compareResult = -1;
                }
                else if (date1.before(date2))
                {
                    compareResult = 1;
                }
                else
                {
                    compareResult = 0;
                }
                return compareResult;
            }
        });

        // return student list
        return recordList;
    }
    // Updating single student
    public int updateRecord(ExerciseRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DURATION, record.getmDuration());
        values.put(KEY_DATE, record.getmDate());
        values.put(KEY_WEIGHT, record.getmWeight());
        values.put(KEY_BODY_PARTS, record.getmBodyParts());
        values.put(KEY_BODY_FAT_PERCENTAGE, record.getmBodyFatPercentage());
        values.put(KEY_PHOTO_PATH, record.getmPhotoPath());
        // updating row
        return db.update(TABLE_EXERCISES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(record.getID()) });
    }
    // Deleting single student
    public void deleteRecord(ExerciseRecord record) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXERCISES, KEY_ID + " = ?",
                new String[] { String.valueOf(record.getID()) });
        db.close();
    }
    // Getting student Count
    public int getRecordsCount() {
        String countQuery = "SELECT * FROM " + TABLE_EXERCISES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }
}