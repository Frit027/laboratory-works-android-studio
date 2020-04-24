package com.mirea.lab7;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;

import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Random;

public class BackendActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ProgressBar progressBar;

    public static SimpleCursorAdapter adapter;
    private HashSet<String> setPos;
    private long pos;

    private SharedPreferences settings;
    private SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backend);

        databaseHelper = new DatabaseHelper(getApplicationContext());

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        settings = getSharedPreferences(MyConstants.NAME_SETTINGS, Context.MODE_PRIVATE);
        setPos = (HashSet<String>) settings.getStringSet(MyConstants.SET_KEY, new HashSet<String>());

        ListView listView = findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.startAnimation(MainActivity.animAlpha);

                if (!setPos.contains(String.valueOf(position))) {
                    prefEditor = settings.edit();
                    setPos.add(String.valueOf(position));
                    prefEditor.putStringSet(MyConstants.SET_KEY, setPos)
                              .apply();

                    Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                    intent.putExtra(MyConstants.ID_KEY, id);
                    intent.putExtra(MyConstants.POSITION_KEY, position);
                    startActivityForResult(intent, MyConstants.REQUEST_ACCESS_TYPE);
                }
            }
        });

        String[] headers = new String[] {DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_PRICE, DatabaseHelper.COLUMN_COUNT};
        adapter = new SimpleCursorAdapter(this, R.layout.list_item, databaseHelper.getAllLines(),
                headers, new int[] {R.id.name, R.id.price, R.id.count}, 0);

        listView.setAdapter(adapter);
    }

    public void onAddItemClick(View view) {
        view.startAnimation(MainActivity.animAlpha);
        Intent intent = new Intent(this, UserActivity.class);
        startActivityForResult(intent, MyConstants.REQUEST_ACCESS_TYPE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == MyConstants.REQUEST_ACCESS_TYPE) {
            if (resultCode == RESULT_OK) {
                pos = data.getIntExtra(MyConstants.POSITION_KEY, -1);

                if (pos != -1) {
                    new ProgressTask(data).execute();
                }
            }
            else {
                setPos.remove(String.valueOf(pos));
                prefEditor.remove(MyConstants.SET_KEY)
                          .putStringSet(MyConstants.SET_KEY, setPos)
                          .commit();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class ProgressTask extends AsyncTask<Void, Integer, Void> {
        private int seconds = new Random().nextInt(2) + 3;
        private int start = 0;
        private long id;
        private String name;
        private String price;
        private String count;

        private ProgressTask(Intent data) {
            id = data.getLongExtra(MyConstants.ID_KEY, 0);
            name = data.getStringExtra(MyConstants.NAME_KEY);
            price = data.getStringExtra(MyConstants.PRICE_KEY);
            count = data.getStringExtra(MyConstants.COUNT_KEY);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(seconds);
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while (start != seconds) {
                publishProgress(start);
                SystemClock.sleep(1000);
                start++;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0] + 1);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (setPos.contains(String.valueOf(pos))) {
                setPos.remove(String.valueOf(pos));
                prefEditor.remove(MyConstants.SET_KEY)
                          .putStringSet(MyConstants.SET_KEY, setPos)
                          .commit();
            }

            Toast toast = Toast.makeText(getApplicationContext(),
                    "Редактирвоание " + name + " завершено", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 580);
            toast.show();

            databaseHelper.update(id, name, price, count);
            adapter.changeCursor(databaseHelper.getAllLines());

            if (StorefrontActivity.adapter != null) {
                PageFragment.products.get((int) pos).setName(name);
                PageFragment.products.get((int) pos).setPrice(price);
                PageFragment.products.get((int) pos).setCount(Integer.parseInt(count));

                if (Integer.parseInt(count) <= 0) {
                    PageFragment.products.remove((int) pos);
                }
                StorefrontActivity.adapter.notifyDataSetChanged();
            }

            if (!setPos.isEmpty()) {
                setPos.clear();
                prefEditor.remove(MyConstants.SET_KEY)
                        .putStringSet(MyConstants.SET_KEY, setPos)
                        .commit();
            }

            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }
}
