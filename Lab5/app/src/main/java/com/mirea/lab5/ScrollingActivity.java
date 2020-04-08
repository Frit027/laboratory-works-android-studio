package com.mirea.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;


public class ScrollingActivity extends AppCompatActivity {
    private static final String PREFS_FILE = "Account";
    private static final String PREF_NAME = "Breed";
    private EditText editText;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE);

        String name = settings.getString(PREF_NAME,"Bengal");
        editText = findViewById(R.id.editText);
        editText.setText(name);
    }

    public void onClickSearch(View view) {
        String breed = editText.getText().toString();

        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString(PREF_NAME, breed);
        prefEditor.apply();

        HashMap<String, String> breeds =
                (HashMap<String, String>) getIntent().getSerializableExtra("HASH_MAP");
        if (breeds.get(breed) != null) {
            String id = breeds.get(breed);
            FragmentManager fm = getSupportFragmentManager();
            RecyclerViewFragment listFragment = RecyclerViewFragment.newInstance(id);

            fm.beginTransaction()
                    .replace(R.id.container, listFragment)
                    .commit();
        }
    }
}
