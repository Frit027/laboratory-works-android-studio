package com.mirea.lab5;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UrlStorage {
    private String TAG = UrlStorage.class.getSimpleName();
    private String id;

    public UrlStorage(String id) {
        this.id = id;
    }

    public ArrayList<String> getData() {
        final ArrayList<String> list = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thecatapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CatAPI catAPI = retrofit.create(CatAPI.class);

        try {
            Response<ArrayList<Breed>> response = catAPI.getUrlImage(id).execute();
            list.add(response.body().get(0).getUrlImage());
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage());
        }

        return list;
    }
}
