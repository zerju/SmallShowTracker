package com.zerju.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity {

    WebView webView;
    TextView webName;
    TextView webDate;
    ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setCollapsible(true);
        toolbar.setTitle("Additional Info");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        webView = (WebView)findViewById(R.id.webView);
        webName = (TextView)findViewById(R.id.webName);
        webDate = (TextView)findViewById(R.id.webDate);
        progressBar2 = (ProgressBar)findViewById(R.id.progressBar2);
        webName.setVisibility(View.GONE);
        webDate.setVisibility(View.GONE);
        webView.setVisibility(View.GONE);
        progressBar2.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        webName.setText(intent.getStringExtra("name"));
        webDate.setText(intent.getStringExtra("date"));

        String url = intent.getStringExtra("url");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

        webName.setVisibility(View.VISIBLE);
        webDate.setVisibility(View.VISIBLE);
        webView.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.GONE);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
