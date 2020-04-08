package com.mirea.lab5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Breed {
    @SerializedName("url")
    @Expose
    private String urlImage;

    public void setId(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getUrlImage() {
        return urlImage;
    }
}
