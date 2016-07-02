package com.zerju.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zerju.movies.gsonEpisodes.Episode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class EpisodesActivity extends AppCompatActivity {

    CheckBox checkBox;
    private ProgressBar spinner;
    TextView  epShowName;
    TextView  epShowDate;
    ListView listView;
    TextView moreInfo;


    public interface ShowEpisodes {
        @GET("shows/{show}?embed=episodes")
        Call<Episode> getShowList(@Path("show") String show);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setCollapsible(true);
        toolbar.setTitle("Episodes");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        final Intent intent = getIntent();

        //ustvaru za povezavo
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.tvmaze.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ShowEpisodes service = retrofit.create(ShowEpisodes.class);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.isIndeterminate();
        spinner.setVisibility(View.VISIBLE);
        epShowName = (TextView)findViewById(R.id.epShowName);
        epShowDate = (TextView)findViewById(R.id.epShowDate);
        listView = (ListView) findViewById(R.id.listView2);
        moreInfo = (TextView)findViewById(R.id.moreInfo);
        moreInfo.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        epShowName.setVisibility(View.GONE);
        epShowDate.setVisibility(View.GONE);

        //pridobi response
        try {
            Call<Episode> call = service.getShowList(intent.getStringExtra("id"));
            call.enqueue(new Callback<Episode>() {
                @Override
                public void onResponse(Response<Episode> response) {
                    //list view s custom adapterjem

                    EpisodesAdapter episodesAdapter = new EpisodesAdapter(getApplicationContext(), response.body().getEmbedded().getEpisodes());
                    episodesAdapter.addAll(response.body().getEmbedded().getEpisodes());
                    listView.setAdapter(episodesAdapter);


                    epShowName.setText(intent.getStringExtra("name"));


                    epShowDate.setText(intent.getStringExtra("date"));


                    spinner.setVisibility(View.GONE);

                    listView.setVisibility(View.VISIBLE);
                    epShowName.setVisibility(View.VISIBLE);
                    epShowDate.setVisibility(View.VISIBLE);
                    moreInfo.setVisibility(View.VISIBLE);
                    //prenese intente oz. podatke na webView activity
                    moreInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           Intent intent2 = new Intent(getApplicationContext(),WebActivity.class);
                           Bundle extras = new Bundle();
                           extras.putString("url",intent.getStringExtra("url"));
                            extras.putString("name",intent.getStringExtra("name"));
                            extras.putString("date",intent.getStringExtra("date"));
                            intent2.putExtras(extras);
                            startActivity(intent2);
                        }
                    });
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("FAILURE", t.getMessage());
                }
            });

        } catch (Exception e) {
            Log.e("IO:", e.getMessage());
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
