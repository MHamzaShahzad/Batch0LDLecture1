package com.dapgarage.lecture1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Map;

public class MyDatabaseManagement {

    private Context context;
    private MyDatabaseOpenHelper myDatabaseOpenHelper;
    private SQLiteDatabase db;
    private static final String TAG = MyDatabaseManagement.class.getName();

    public MyDatabaseManagement(Context context) {
        this.context = context;
        myDatabaseOpenHelper = new MyDatabaseOpenHelper(context);
        db = myDatabaseOpenHelper.getWritableDatabase();
    }


    public boolean create(String name, String email) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        long inserted = db.insert(MyDatabaseOpenHelper.CRUD_TABLE, null, values);
        Log.i(TAG, "create: " + inserted);
        return inserted != -1;
    }

    public void read() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + MyDatabaseOpenHelper.CRUD_TABLE, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                Log.i(TAG, "read: ID: " + id + " NAME: " + name + " EMAIL: " + email);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public boolean update(String id, String name, String email) {


        ContentValues contentValues = new ContentValues();
        if (name != null)
            contentValues.put("name", name);
        if (email != null)
            contentValues.put("email", email);

        long row = db.update(MyDatabaseOpenHelper.CRUD_TABLE, contentValues, "id = " + id, null);
        return row != -1;
    }

    public boolean delete(String id) {
        int count = db.delete(MyDatabaseOpenHelper.CRUD_TABLE, "id = " + id, null);
        return count > 0;
    }

}
