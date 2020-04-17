package com.mirea.lab6;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Product> productList;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<Product> list) {
        super(fm);
        productList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return(PageFragment.newInstance(position, productList));
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return productList.size();
    }
}
