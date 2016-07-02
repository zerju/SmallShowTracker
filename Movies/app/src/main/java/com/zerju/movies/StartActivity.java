package com.zerju.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    Button favoriteShowsBtn;
    Button searchShowsBtn;
    Button watchedShowsBtn;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Small Show Tracker");
        toolbar.setCollapsible(true);
        setSupportActionBar(toolbar);

        anim = AnimationUtils.loadAnimation(StartActivity.this, R.anim.anim_alpha);
        favoriteShowsBtn = (Button)findViewById(R.id.favoriteShowsBtn);
        searchShowsBtn = (Button)findViewById(R.id.searchShowsBtn);
        watchedShowsBtn = (Button)findViewById(R.id.watchedShowsBtn);

        searchShowsBtn.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            @Override
            public void onGlobalLayout() {

                searchShowsBtn.startAnimation(anim);

                //deprecated in API level 16
                searchShowsBtn.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //for API Level >= 16
                //anImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }});
        favoriteShowsBtn.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            @Override
            public void onGlobalLayout() {

                favoriteShowsBtn.startAnimation(anim);

                //deprecated in API level 16
                favoriteShowsBtn.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //for API Level >= 16
                //anImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }});

        watchedShowsBtn.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            @Override
            public void onGlobalLayout() {

                watchedShowsBtn.startAnimation(anim);

                //deprecated in API level 16
                watchedShowsBtn.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //for API Level >= 16
                //anImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }});

        searchShowsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


        favoriteShowsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FavoriteActivity.class));
            }
        });

        watchedShowsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),WatchedActivity.class));
            }
        });

    }


}
