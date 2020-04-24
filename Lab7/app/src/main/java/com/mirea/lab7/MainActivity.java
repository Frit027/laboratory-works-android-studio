package com.mirea.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {

    public static Animation animAlpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
    }

    public void onStartStorefrontClick(View view) {
        view.startAnimation(animAlpha);
        Intent intent = new Intent(this, StorefrontActivity.class);
        startActivity(intent);
    }

    public void onStartBackendClick(View view) {
        view.startAnimation(animAlpha);
        Intent intent = new Intent(this, BackendActivity.class);
        startActivity(intent);
    }
}
