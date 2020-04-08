package com.mirea.lab5;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatAPI {
    @GET("images/search?")
    Call<ArrayList<Breed>> getUrlImage(@Query("breed_ids") String breedID);
}
