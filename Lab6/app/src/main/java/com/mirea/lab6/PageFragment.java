package com.mirea.lab6;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class PageFragment extends Fragment {

    private static final String NAME_KEY = "NAME";
    private static final String PRICE_KEY = "PRICE";
    private static final String COUNT_KEY = "COUNT";

    private String name;
    private String price;
    private int count;

    public PageFragment() { }

    public static PageFragment newInstance(int i, ArrayList<Product> productList) {
        PageFragment fragment = new PageFragment();

        Bundle args = new Bundle();
        args.putString(NAME_KEY, productList.get(i).getName());
        args.putString(PRICE_KEY, productList.get(i).getPrice());
        args.putInt(COUNT_KEY, productList.get(i).getCount());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = getArguments().getString(NAME_KEY);
        price = getArguments().getString(PRICE_KEY);
        count = getArguments().getInt(COUNT_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        TextView nameTextView = view.findViewById(R.id.nameText);
        nameTextView.setText(name);

        TextView priceTextView = view.findViewById(R.id.priceText);
        priceTextView.setText(String.format("Цена: %s", price));

        TextView countTextView = view.findViewById(R.id.countText);
        countTextView.setText(String.format("Количество: %s", count));

        return view;
    }
}
