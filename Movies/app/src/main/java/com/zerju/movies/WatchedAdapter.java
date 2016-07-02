package com.zerju.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by System32 on 7. 02. 2016.
 */
public class WatchedAdapter extends ArrayAdapter<Object> {

    List<Watched> watched;
    Context c;
    LayoutInflater inflater;

    public WatchedAdapter(Context context, List<Watched> watched){
        super(context,R.layout.movie_row);
        this.watched = watched;
        this.c = context;

    }

    public class ViewHolder{
        TextView name;
        TextView year;
        TextView status;
        ImageView listImageView;
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent){
        View favoriteView = convertView;
        if (favoriteView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            favoriteView = inflater.inflate(R.layout.movie_row, parent,false);
            //convertView = favoriteView;
        }



        final ViewHolder holder = new ViewHolder();

        holder.name = (TextView) favoriteView.findViewById(R.id.nameShowText);
        holder.year = (TextView) favoriteView.findViewById(R.id.yearShowText);
        holder.status = (TextView) favoriteView.findViewById(R.id.statusShowText);
        holder.listImageView = (ImageView) favoriteView.findViewById(R.id.listImageView);

        final MaterialFavoriteButton favoriteB = (MaterialFavoriteButton)favoriteView.findViewById(R.id.favoriteButtonW);
        favoriteB.setVisibility(View.VISIBLE);


        //vcasih ni slik
        if(watched.get(position).isObstajaSlika()){
            Picasso.with(c).load(watched.get(position).getImage()).into(holder.listImageView);
            favoriteB.setTag(R.string.fifthId,"true");
            favoriteB.setTag(R.string.sixthId, watched.get(position).getImage());
        }else{
            holder.listImageView.setImageResource(R.drawable.no_image_available);
            favoriteB.setTag(R.string.fifthId, "false");
        }

        holder.name.setText(watched.get(position).getName());
        holder.status.setText(watched.get(position).getStatus());
        holder.year.setText(watched.get(position).getDate());
        favoriteB.setTag(R.string.thirdId, watched.get(position).getDate());
        holder.name.setTag(watched.get(position).getIdShow());
        holder.year.setTag(watched.get(position).getUrl());

        //final MaterialFavoriteButton favoriteB = (MaterialFavoriteButton)favoriteView.findViewById(R.id.favoriteButtonW);

        //favoriteB.setTag(watched.get(position).getIdShow());
        favoriteB.setTag(R.string.fristId, watched.get(position).getIdShow());
        favoriteB.setTag(R.string.secondId,watched.get(position).getName());
        favoriteB.setTag(R.string.fourthId,watched.get(position).getStatus());

        favoriteB.setTag(R.string.seventhId,watched.get(position).getUrl());

        //inicializacija
        Realm realm = Realm.getInstance(c);


        //ali obstaja zapis
        RealmQuery<Favorite> query = realm.where(Favorite.class);
        query.equalTo("idShow", watched.get(position).getIdShow());
        RealmResults<Favorite> result1 = query.findAll();

        if(result1.size()>0){
            favoriteB.setType(MaterialFavoriteButton.STYLE_HEART);
            favoriteB.setFavorite(true);

        }else{//da se kr vsi povprek ne spreminjajo v pobarvane srcke
            favoriteB.setType(MaterialFavoriteButton.STYLE_HEART);
            favoriteB.setFavorite(false);
        }



        favoriteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favorite favorite = new Favorite();


                TextView name = (TextView)v.findViewById(R.id.nameShowText);
                TextView date = (TextView)v.findViewById(R.id.yearShowText);
                TextView status = (TextView)v.findViewById(R.id.statusShowText);
                ImageView image = (ImageView)v.findViewById(R.id.listImageView);
                Realm realm = Realm.getInstance(c);
                RealmQuery<Favorite> query = realm.where(Favorite.class);
                query.equalTo("idShow", v.findViewById(R.id.favoriteButtonW).getTag(R.string.fristId).toString());
                RealmResults<Favorite> result1 = query.findAll();

                if(result1.size()>0){//zbrise iz favorite
                    Toast.makeText(c, "Unfavorited", Toast.LENGTH_SHORT).show();
                    favoriteB.setType(MaterialFavoriteButton.STYLE_HEART);
                    favoriteB.setFavorite(false);

                    String id = v.findViewById(R.id.favoriteButtonW).getTag(R.string.fristId).toString();
                    favoriteB.setColor(MaterialFavoriteButton.STYLE_BLACK);
                    RealmResults<Favorite> results = realm.where(Favorite.class).equalTo("idShow",id).findAll();
                    realm.beginTransaction();
                    results.remove(0);
                    realm.commitTransaction();

                }else{//doda k favorite
                    favoriteB.setType(MaterialFavoriteButton.STYLE_HEART);
                    favoriteB.setFavorite(true);
                    Toast.makeText(c,"Favorited",Toast.LENGTH_SHORT).show();

                    favorite.setIdShow(v.findViewById(R.id.favoriteButtonW).getTag(R.string.fristId).toString());
                    favorite.setName(v.findViewById(R.id.favoriteButtonW).getTag(R.string.secondId).toString());
                    favorite.setDate(v.findViewById(R.id.favoriteButtonW).getTag(R.string.thirdId).toString());
                    favorite.setStatus(v.findViewById(R.id.favoriteButtonW).getTag(R.string.fourthId).toString());
                    favorite.setUrl(v.findViewById(R.id.favoriteButtonW).getTag(R.string.seventhId).toString());
                    if(Boolean.parseBoolean(v.findViewById(R.id.favoriteButtonW).getTag(R.string.fifthId).toString())){
                        favorite.setImage(v.findViewById(R.id.favoriteButtonW).getTag(R.string.sixthId).toString());
                        favorite.setObstajaSlika(true);
                    }else{
                        favorite.setObstajaSlika(false);
                    }
                    realm.beginTransaction();
                    realm.copyToRealm(favorite);
                    realm.commitTransaction();
                }
            }
        });


        return favoriteView;
    }


}

