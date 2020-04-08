package com.mirea.lab5;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import java.util.ArrayList;

public class MyPositionalDataSource extends PositionalDataSource<String> {
    private final UrlStorage urlStorage;

    public MyPositionalDataSource(UrlStorage urlStorage) {
        this.urlStorage = urlStorage;
    }

    @Override
    public void loadInitial(@NonNull PositionalDataSource.LoadInitialParams params,
                            @NonNull LoadInitialCallback<String> callback) {
        ArrayList<String> result = urlStorage.getData();
        callback.onResult(result, 0);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params,
                          @NonNull LoadRangeCallback<String> callback) {
        ArrayList<String> result = urlStorage.getData();
        callback.onResult(result);
    }

}