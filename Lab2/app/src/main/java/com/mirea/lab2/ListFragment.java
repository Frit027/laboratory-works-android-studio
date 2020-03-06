package com.mirea.lab2;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;


public class ListFragment extends Fragment {

    private ArrayList<HashMap<String, String>> technologyList;
    private OnFragmentInteractionListener mListener;
    private int pos;
    private final static String KEY_LIST = "LIST";

    public static ListFragment newInstance(ArrayList<HashMap<String, String>> list) {
        ListFragment fragment = new ListFragment();

        Bundle args = new Bundle();
        args.putSerializable(KEY_LIST, list);
        fragment.setArguments(args);

        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        technologyList = (ArrayList<HashMap<String, String>>) getArguments().getSerializable(KEY_LIST);
        View view = inflater.inflate(R.layout.recycler_view_layout, null);

        RecyclerView recyclerView = view.findViewById(R.id.list);
        DataAdapter adapter = new DataAdapter(getActivity(), technologyList);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        pos = position;
                        updateDetail();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    interface OnFragmentInteractionListener {

        void onFragmentInteraction(int pos);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    public void updateDetail() {
        mListener.onFragmentInteraction(pos);
    }
}
