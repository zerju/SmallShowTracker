package com.zerju.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zerju.movies.gsonClasses.Show;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import com.github.ivbaranov.mfb.*;

import org.w3c.dom.Text;

/**
 * Created by Jure on 4. 02. 2016.
 */
public class SearchAdapter extends ArrayAdapter<Object> {

    List<Show> searched;
    Context c;
    LayoutInflater inflater;
    TextView name;
    int id1 = 1;
    int id2 = 2;

    public SearchAdapter(Context context, List<Show> searched){
        super(context,R.layout.movie_row);
        this.searched = searched;
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
        View searchView = convertView;
        final ViewGroup parent2 = parent;
        if (searchView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            searchView = inflater.inflate(R.layout.movie_row, parent,false);
            //convertView = searchView;
        }
        final View searchView2 = searchView;
        if(position<searched.size()-1) {


            final ViewHolder holder = new ViewHolder();

            holder.name = (TextView) searchView.findViewById(R.id.nameShowText);
            holder.year = (TextView) searchView.findViewById(R.id.yearShowText);
            holder.status = (TextView) searchView.findViewById(R.id.statusShowText);
            holder.listImageView = (ImageView) searchView.findViewById(R.id.listImageView);

            final MaterialFavoriteButton favoriteB = (MaterialFavoriteButton)searchView.findViewById(R.id.favoriteButtonW);
            favoriteB.setVisibility(View.VISIBLE);

            //vcasih ni slik
            if(searched.get(position).getShow().getImage()!=null){
                Picasso.with(c).load(searched.get(position).getShow().getImage().getMedium().toString()).into(holder.listImageView);
                holder.listImageView.setTag(R.string.fristId, "true");
                holder.listImageView.setTag(R.string.secondId, searched.get(position).getShow().getImage().getMedium().toString());
                favoriteB.setTag(R.string.fifthId,"true");
                favoriteB.setTag(R.string.sixthId,searched.get(position).getShow().getImage().getMedium().toString());
            }else{
                holder.listImageView.setImageResource(R.drawable.no_image_available);
                holder.listImageView.setTag(R.string.fristId, "false");
                favoriteB.setTag(R.string.fifthId,"false");
            }

            holder.name.setText(searched.get(position).getShow().getName());
            holder.status.setText(searched.get(position).getShow().getStatus());
            holder.status.setTag(R.string.fristId, searched.get(position).getShow().getStatus());
            holder.status.setTag(R.string.secondId,searched.get(position).getShow().getName());

                holder.name.setTag(searched.get(position).getShow().getId());
            //vcasih ni datuma in leta
            if(searched.get(position).getShow().getPremiered()!=null){
                holder.year.setText(" ("+searched.get(position).getShow().getPremiered().substring(0, 4)+")");
                holder.status.setTag(R.string.thirdId, " (" + searched.get(position).getShow().getPremiered().substring(0, 4) + ")");
                favoriteB.setTag(R.string.thirdId," ("+searched.get(position).getShow().getPremiered().substring(0, 4)+")");
            }
            holder.year.setTag(searched.get(position).getShow().getUrl());



            favoriteB.setTag(R.string.fristId,searched.get(position).getShow().getId());
            favoriteB.setTag(R.string.secondId,searched.get(position).getShow().getName());
            favoriteB.setTag(R.string.fourthId,searched.get(position).getShow().getStatus());

            favoriteB.setTag(R.string.seventhId,searched.get(position).getShow().getUrl());

            //inicializacija
            Realm realm = Realm.getInstance(c);

            //ali obstaja zapis
            RealmQuery<Favorite> query = realm.where(Favorite.class);
            query.equalTo("idShow", Integer.toString(searched.get(position).getShow().getId()));
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

                    //inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    //View searchView = inflater.inflate(R.layout.movie_row, parent2,false);


                    name = (TextView)searchView2.findViewById(R.id.nameShowText);
                    TextView date = (TextView)searchView2.findViewById(R.id.yearShowText);
                    TextView status = (TextView)searchView2.findViewById(R.id.statusShowText);
                    ImageView image = (ImageView)searchView2.findViewById(R.id.listImageView);
                    Realm realm = Realm.getInstance(c);
                    RealmQuery<Favorite> query = realm.where(Favorite.class);
                    query.equalTo("idShow", v.findViewById(R.id.favoriteButtonW).getTag(R.string.fristId).toString());
                    RealmResults<Favorite> result1 = query.findAll();

                    if(result1.size()>0){//zbrise iz favorite
                        Toast.makeText(c,"Unfavorited",Toast.LENGTH_SHORT).show();
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
            }

        return searchView;
    }


}
