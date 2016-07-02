package com.zerju.movies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class FavoriteActivity extends AppCompatActivity {

    ListView listView3;
    public static String idShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setCollapsible(true);
        toolbar.setTitle("Favorite Shows");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Realm realm = Realm.getInstance(getApplicationContext());
        RealmQuery<Favorite> query = realm.where(Favorite.class);
        query.findAll();
        RealmResults<Favorite> result1 = query.findAll();

        listView3 = (ListView)findViewById(R.id.listView3);
        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(getApplicationContext(), result1);
        favoriteAdapter.addAll(result1);
        listView3.setAdapter(favoriteAdapter);

        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String showId = ((TextView) view.findViewById(R.id.nameShowText)).getTag().toString();
                idShow = showId;
                SharedPreferences prefs;
                prefs = getApplicationContext().getSharedPreferences("showId", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("ID",idShow);
                editor.commit();
                MainActivity.idShow = idShow;

                String showName = ((TextView) view.findViewById(R.id.nameShowText)).getText().toString();
                String showDate = ((TextView) view.findViewById(R.id.yearShowText)).getText().toString();
                String url = ((TextView) view.findViewById(R.id.yearShowText)).getTag().toString();
                ImageView image = (ImageView) view.findViewById(R.id.listImageView);
                TextView status = (TextView) view.findViewById(R.id.statusShowText);
                MaterialFavoriteButton fav = (MaterialFavoriteButton)view.findViewById(R.id.favoriteButtonW);


                Intent intent = new Intent(getApplicationContext(), EpisodesActivity.class);
                Bundle extras = new Bundle();

                extras.putString("id", showId);
                extras.putString("name", showName);
                extras.putString("date", showDate);
                extras.putString("url", url);//url za webView
                Watched watched = new Watched();
                watched.setDate(showDate);
                watched.setName(showName);
                watched.setUrl(url);
                if(Boolean.parseBoolean(fav.getTag(R.string.fifthId).toString())){
                    watched.setImage(fav.getTag(R.string.sixthId).toString());
                    watched.setObstajaSlika(true);
                }else{
                    watched.setObstajaSlika(false);
                }
                watched.setStatus(status.getText().toString());

                MainActivity.watched = watched;
                intent.putExtras(extras);

                startActivity(intent);


            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
