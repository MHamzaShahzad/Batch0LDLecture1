package com.dapgarage.lecture1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CRUD_DB";
    public static final String CRUD_TABLE = "CRUD_TABLE";
    public static final int DATABASE_VERSION = 1;

    public MyDatabaseOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CRUD_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CRUD_TABLE");
        onCreate(db);
    }
}
