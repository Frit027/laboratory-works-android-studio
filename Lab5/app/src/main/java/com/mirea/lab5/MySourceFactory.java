package com.mirea.lab5;

import androidx.paging.DataSource;

public class MySourceFactory extends DataSource.Factory<Integer, String> {
    private final UrlStorage urlStorage;

    MySourceFactory(UrlStorage urlStorage) {
        this.urlStorage = urlStorage;
    }

    @Override
    public DataSource create() {
        return new MyPositionalDataSource(urlStorage);
    }
}
