package com.mirea.lab3_2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private DatabaseHelper database;
    private Cursor cursor;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        database = new DatabaseHelper(getApplicationContext());
        listView = findViewById(R.id.list);
    }

    @Override
    public void onResume() {
        super.onResume();

        db = database.getReadableDatabase();
        cursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_NAME, null);

        String[] data = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_LAST_NAME,
                DatabaseHelper.COLUMN_FIRST_NAME, DatabaseHelper.COLUMN_PATRONYMIC, DatabaseHelper.COLUMN_TIME_TO_ADD};
        cursor.moveToFirst();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_item,
                cursor, data, new int[] {R.id.text1, R.id.text2, R.id.text3, R.id.text4, R.id.text5}, 0);

        listView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        db.close();
        cursor.close();
    }
}
