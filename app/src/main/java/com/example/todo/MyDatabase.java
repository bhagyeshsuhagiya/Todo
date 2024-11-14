package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "ToDo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "My_ToDo";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TASK = "task";
    private static final String COLUMN_TASK_DESCRIPTION = "description";

    public MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TASK + " TEXT, "
                + COLUMN_TASK_DESCRIPTION + " TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addTask(String task, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASK, task);
        cv.put(COLUMN_TASK_DESCRIPTION, description);

        long result = db.insert(TABLE_NAME, null, cv);
        if (context != null) {
            if (result == -1) {
                Toast.makeText(context, "Failed to add task", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Task added successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public boolean updateData(String id, String task, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASK, task);
        cv.put(COLUMN_TASK_DESCRIPTION, description);

        long result = db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{id});
        if (result == -1) {
            if (context != null) {
                Toast.makeText(context, "Failed to update task", Toast.LENGTH_SHORT).show();
            }
            return false;
        } else {
            if (context != null) {
                Toast.makeText(context, "Task updated successfully", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }

    public boolean deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,  "id=?", new String[]{row_id});
        if (result == -1) {
            if (context != null) {
                Toast.makeText(context, "Failed to delete task", Toast.LENGTH_SHORT).show();
            }
            return false;
        } else {
            if (context != null) {
                Toast.makeText(context, "Task deleted successfully", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }
}
