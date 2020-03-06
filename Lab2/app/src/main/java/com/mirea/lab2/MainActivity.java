package com.mirea.lab2;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener {

    private ArrayList<HashMap<String, String>> technologyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        technologyList = (ArrayList<HashMap<String, String>>) getIntent()
                .getSerializableExtra("ARRAY_LIST");

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            ListFragment listFragment = ListFragment.newInstance(technologyList);

            fm.beginTransaction()
              .add(R.id.container, listFragment)
              .commit();
        }
    }

    @Override
    public void onFragmentInteraction(int position) {
        FragmentManager fm = getSupportFragmentManager();
        ViewPagerFragment secondFragment = ViewPagerFragment.newInstance(technologyList, position);

        fm.beginTransaction()
          .replace(R.id.container, secondFragment)
          .addToBackStack(null)
          .commit();
    }
}
