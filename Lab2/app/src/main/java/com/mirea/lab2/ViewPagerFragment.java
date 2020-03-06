package com.mirea.lab2;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;


public class ViewPagerFragment extends Fragment {

    private ArrayList<HashMap<String, String>> technologyList;
    private int position;
    private final static String KEY_INT = "INT";
    private final static String KEY_LIST = "LIST";

    public static ViewPagerFragment newInstance(ArrayList<HashMap<String, String>> list, int position) {
        ViewPagerFragment fragment = new ViewPagerFragment();

        Bundle args = new Bundle();
        args.putSerializable(KEY_LIST, list);
        args.putInt(KEY_INT, position);
        fragment.setArguments(args);

        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        technologyList =
                (ArrayList<HashMap<String, String>>) getArguments().getSerializable(KEY_LIST);
        position = getArguments().getInt(KEY_INT);

        View view = inflater.inflate(R.layout.view_pager_layout, null);

        ViewPager2 viewPager2 = view.findViewById(R.id.viewPager2);
        viewPager2.setAdapter(new ViewPagerAdapter(getActivity(), technologyList, viewPager2));
        viewPager2.setCurrentItem(position);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });

        return view;
    }
}
