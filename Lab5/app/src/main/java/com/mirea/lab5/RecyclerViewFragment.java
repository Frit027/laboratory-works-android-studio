package com.mirea.lab5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.Executors;


public class RecyclerViewFragment extends Fragment {
    public static RecyclerViewFragment newInstance(String id) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();

        Bundle args = new Bundle();
        args.putString("ID", id);
        fragment.setArguments(args);

        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_layout, null);

        RecyclerView recyclerView = view.findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        String id = getArguments().getString("ID");
        MySourceFactory sourceFactory = new MySourceFactory(new UrlStorage(id));

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(5)
                .build();

        LiveData<PagedList<String>> pagedListLiveData = new LivePagedListBuilder<>(sourceFactory, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build();

        final ListAdapter adapter = new ListAdapter(new UrlDiffUtilCallback());

        pagedListLiveData.observe(this, new Observer<PagedList<String>>() {
            @Override
            public void onChanged(@Nullable PagedList<String> list) {
                adapter.submitList(list);
            }
        });

        recyclerView.setAdapter(adapter);

        return view;
    }
}
