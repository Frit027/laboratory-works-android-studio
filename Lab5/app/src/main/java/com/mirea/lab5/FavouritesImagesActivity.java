package com.mirea.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class FavouritesImagesActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private Cursor cursor;
    private SQLiteDatabase db;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_images);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        listView = findViewById(R.id.imagesList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = databaseHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);

        ArrayList<String> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_URL));

                list.add(name);
                cursor.moveToNext();
            }
        }

        ImageAdapter adapter = new ImageAdapter(this, R.layout.list_item, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        cursor.close();
    }
}
