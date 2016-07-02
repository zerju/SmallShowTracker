package com.zerju.movies;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zerju.movies.gsonEpisodes.Episode_;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Jure on 4. 02. 2016.
 */
public class EpisodesAdapter extends ArrayAdapter<Object> {

    List<Episode_> searched;
    Context c;
    LayoutInflater inflater;

    public EpisodesAdapter(Context context, List<Episode_> searched) {
        super(context, R.layout.movie_row);
        this.searched = searched;
        this.c = context;

    }

    //holder za elemente
    public class ViewHolder {
        TextView season;
        TextView episode;
        TextView epName;
        TextView moreInfo;
        TextView airedText;
        ImageView listImageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View searchView = convertView;
        if (searchView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            searchView = inflater.inflate(R.layout.episode_row, parent, false);

        }


        final ViewHolder holder = new ViewHolder();


        //inicializacija
        holder.season = (TextView) searchView.findViewById(R.id.seasonText);
        holder.episode = (TextView) searchView.findViewById(R.id.episodeText);
        holder.epName = (TextView) searchView.findViewById(R.id.epNameText);
        holder.moreInfo = (TextView) searchView.findViewById(R.id.moreInfo);
        holder.listImageView = (ImageView) searchView.findViewById(R.id.episodeImage);
        holder.airedText = (TextView) searchView.findViewById(R.id.airedText);



        //vcasih ni slik in moram zato preverit
        if (searched.get(position).getImage() != null){
            Picasso.with(c).load(searched.get(position).getImage().getMedium().toString()).into(holder.listImageView);
            holder.listImageView.setTag(R.string.fristId, "true");
            holder.listImageView.setTag(R.string.secondId, searched.get(position).getImage().getMedium().toString());
        }else{
            holder.listImageView.setImageResource(R.drawable.no_image_available);
            holder.listImageView.setTag(R.string.fristId, "false");
        }

        holder.season.setText("S" + searched.get(position).getSeason().toString());
        holder.episode.setText("E" + searched.get(position).getNumber().toString());
        holder.epName.setText(searched.get(position).getName());
        //vcasih je airdate null
        if (searched.get(position).getAirdate() != null)
            holder.airedText.setText(searched.get(position).getAirdate());

        SharedPreferences prefs = c.getSharedPreferences("showId",Context.MODE_PRIVATE);

        //pridobi idShowa iz mainActivity = static
        final String saveShowId = prefs.getString("ID","");
        //Log.e("NEKAJ",saveShowId);
        final String saveEpId = Integer.toString(searched.get(position).getId());
        final CheckBox checkBox = (CheckBox) searchView.findViewById(R.id.checkBox);

        //inicializacija baze
        Realm realm = Realm.getInstance(c);

        //ali ze obstaja ta zapis
        RealmQuery<Watched> query = realm.where(Watched.class);
        query.equalTo("idShow", saveShowId).equalTo("idEpisode", saveEpId);
        RealmResults<Watched> result1 = query.findAll();



        if (result1.size() > 0) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        final Watched clicked = MainActivity.watched;

        //odstrani ali doda v bazo
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inicializacija baze
                Realm realm = Realm.getInstance(c);
                RealmQuery<Watched> query2 = realm.where(Watched.class);
                query2.equalTo("idShow", saveShowId);
                RealmResults<Watched> result2 = query2.findAll();
                final int found = result2.size();
                if (checkBox.isChecked()) {//doda
                    Watched watched = new Watched();
                   // Realm realm = Realm.getInstance(c);
                    watched.setIdShow(saveShowId);
                    watched.setIdEpisode(saveEpId);
                    watched.setName("");
                    //Log.e("SIZE",""+found);
                    if(found == 0) {
                       // Log.e("IF","asdasda");
                        watched.setDate(clicked.getDate());
                        watched.setName(clicked.getName());
                        watched.setStatus(clicked.getStatus());
                        watched.setUrl(clicked.getUrl());
                        watched.setObstajaSlika(clicked.isObstajaSlika());
                        if (clicked.isObstajaSlika()) {
                            watched.setImage(clicked.getImage());
                        }
                    }
                    Toast.makeText(c, "Added to watched", Toast.LENGTH_SHORT).show();
                    realm.beginTransaction();
                    realm.copyToRealm(watched);
                    realm.commitTransaction();

                } else {//odstrani
                    Toast.makeText(c, "Removed from watched", Toast.LENGTH_SHORT).show();
                    //Realm realm = Realm.getInstance(c);
                    RealmResults<Watched> results = realm.where(Watched.class).equalTo("idEpisode", saveEpId).findAll();
                    realm.beginTransaction();
                    results.remove(0);
                    realm.commitTransaction();
                }
            }
        });


        return searchView;
    }


}

