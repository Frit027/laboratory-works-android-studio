package com.mirea.lab5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "images.db";
    private static final int SCHEMA = 1;
    static final String TABLE = "images";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_URL = "url";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE images (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_URL
                + " TEXT);");
    }

    public void insert(String url) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_URL, url);
        db.insert(TABLE, null, values);

        checkSizeDatabase(db);
    }

    private void checkSizeDatabase(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);
        if (cursor.getCount() > 10) {
            cursor.moveToFirst();
            String id = cursor.getString(0);

            db.delete(TABLE, "_id = ?", new String[]{id});
        }
        cursor.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
