package com.zerju.movies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


import com.zerju.movies.gsonClasses.Show;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    public static String idShow;
    public static Watched watched;
    SharedPreferences prefs;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //dinamicno pridobivanje - sam doda na konec parameter
    public interface MovieService {
        @GET("search/shows")
        Call<List<Show>> getShowList(@Query("q") String show);
    }

    public interface MovieSearch {
        @GET("search/shows?q={show}")
        Call<List<Show>> getShowList(@Path("show") String show);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setCollapsible(true);
        toolbar.setTitle("Search Shows");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search");

        //*** setOnQueryTextListener ***
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.tvmaze.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            @Override
            public boolean onQueryTextSubmit(String query) {//ko klikne isci
                // TODO Auto-generated method stub

                try {
                    MovieSearch service = retrofit.create(MovieSearch.class);
                    Call<List<Show>> call = service.getShowList(query);

                    call.enqueue(new Callback<List<Show>>() {
                        @Override
                        public void onResponse(Response<List<Show>> response) {
                            ListView listView = (ListView) findViewById(R.id.listView);
                            SearchAdapter searchAdapter = new SearchAdapter(getApplicationContext(), response.body());
                            searchAdapter.addAll(response.body());

                            listView.setAdapter(searchAdapter);
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("FAILURE", t.getMessage());
                        }
                    });

                } catch (Exception e) {
                    Log.e("IO:", e.getMessage());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {//dinamicno iskanje
                // TODO Auto-generated method stub
                MovieService service = retrofit.create(MovieService.class);
                try {
                    Call<List<Show>> call = service.getShowList(newText);
                    call.enqueue(new Callback<List<Show>>() {
                        @Override
                        public void onResponse(Response<List<Show>> response) {

                            ListView listView = (ListView) findViewById(R.id.listView);
                            SearchAdapter searchAdapter = new SearchAdapter(getApplicationContext(), response.body());
                            searchAdapter.addAll(response.body());

                            listView.setAdapter(searchAdapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String showId = ((TextView) view.findViewById(R.id.nameShowText)).getTag().toString();
                                    idShow = showId;
                                    prefs = getApplicationContext().getSharedPreferences("showId", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("ID",idShow);
                                    editor.commit();
                                    String showName = ((TextView) view.findViewById(R.id.nameShowText)).getText().toString();
                                    String showDate = ((TextView) view.findViewById(R.id.yearShowText)).getText().toString();
                                    String url = ((TextView) view.findViewById(R.id.yearShowText)).getTag().toString();
                                    ImageView image = (ImageView)view.findViewById(R.id.listImageView);
                                    TextView status = (TextView)view.findViewById(R.id.statusShowText);


                                    Intent intent = new Intent(getApplicationContext(), EpisodesActivity.class);
                                    Bundle extras = new Bundle();

                                    extras.putString("id", showId);
                                    extras.putString("name", showName);
                                    extras.putString("date", showDate);
                                    extras.putString("url", url);//url za webView
                                    watched = new Watched();
                                    watched.setDate(showDate);
                                    watched.setName(showName);
                                    watched.setUrl(url);
                                    if(Boolean.parseBoolean(image.getTag(R.string.fristId).toString())){
                                        watched.setImage(image.getTag(R.string.secondId).toString());
                                        watched.setObstajaSlika(true);
                                    }else{
                                        watched.setObstajaSlika(false);
                                    }
                                    watched.setStatus(status.getText().toString());


                                    intent.putExtras(extras);

                                    startActivity(intent);


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
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
