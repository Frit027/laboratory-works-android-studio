package com.mirea.lab3_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper sqlHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqlHelper = new DatabaseHelper(getApplicationContext());
        db = sqlHelper.getWritableDatabase();
        onUpdate();
    }

    private void onUpdate() {
        cursor = db.rawQuery("select " + DatabaseHelper.COLUMN_ID + " from " + DatabaseHelper.TABLE_NAME, null);

        ArrayList<String> idList = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(0);
                idList.add(id);
            }
            while(cursor.moveToNext());
        }
        cursor.close();

        for (String id : idList) {
            db.delete(DatabaseHelper.TABLE_NAME, "_id = ?", new String[]{ id });
        }
        sqlHelper.insertData(db);
    }

    public void onClickShow(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    public void onClickAdd(View view) {
        ContentValues values = new ContentValues();
        String name = sqlHelper.getName(db);
        String[] words = name.split("\\s");

        values.put(DatabaseHelper.COLUMN_FIRST_NAME, words[1]);
        values.put(DatabaseHelper.COLUMN_LAST_NAME, words[0]);
        values.put(DatabaseHelper.COLUMN_PATRONYMIC, words[2]);

        db.insert(DatabaseHelper.TABLE_NAME, null, values);
    }

    public void onClickChange(View view) {
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.COLUMN_FIRST_NAME, "Иван");
        values.put(DatabaseHelper.COLUMN_LAST_NAME, "Иванов");
        values.put(DatabaseHelper.COLUMN_PATRONYMIC, "Иванович");

        cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME
                + " WHERE " + DatabaseHelper.COLUMN_ID + " = (SELECT MAX("
                + DatabaseHelper.COLUMN_ID + ") FROM " + DatabaseHelper.TABLE_NAME + ");", null);
        cursor.moveToFirst();

        String id = cursor.getString(0);
        db.update(DatabaseHelper.TABLE_NAME, values, "_id = ?", new String[] { id });
        cursor.close();
    }
}
