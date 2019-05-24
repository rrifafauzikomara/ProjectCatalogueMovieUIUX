package com.example.rifafauzi6.projectcataloguemovieuiux.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rifafauzi6.projectcataloguemovieuiux.MovieItemClickListener;
import com.example.rifafauzi6.projectcataloguemovieuiux.view.DetailMovieActivity;
import com.example.rifafauzi6.projectcataloguemovieuiux.model.Movies;
import com.example.rifafauzi6.projectcataloguemovieuiux.R;
import com.example.rifafauzi6.projectcataloguemovieuiux.view.MainActivity;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

//    private MovieItemClickListener movieItemClickListener;
    private Context context;
    private List<Movies> listMovies;

    public MovieAdapter(Context context, List<Movies> listMovies){
        this.context = context;
        this.listMovies = listMovies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, null, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Movies movies = listMovies.get(position);
        Glide.with(holder.itemView.getContext())
                .load("http://image.tmdb.org/t/p/w185"+movies.getPosterPath())
                .placeholder(R.drawable.img_default_bg)
                .into(holder.gmb);
        holder.judul.setText(movies.getTitle());
        holder.desc.setText(movies.getOverview());
        holder.tgl.setText(movies.getReleaseDate());

        ViewCompat.setTransitionName(holder.gmb, movies.getPosterPath());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                movieItemClickListener.onMovieItemClick(movies, holder.gmb);
                Intent i = new Intent(context, DetailMovieActivity.class);
                i.putExtra("title", movies.getTitle());
                i.putExtra("poster_path", movies.getPosterPath());
                i.putExtra("overview", movies.getOverview());
                i.putExtra("release_date", movies.getReleaseDate());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

//                Intent intent = new Intent(context, DetailMovieActivity.class)
//                        .putExtra("title", movies.getTitle())
//                        .putExtra("poster_path", movies.getPosterPath())
//                        .putExtra("overview", movies.getOverview())
//                        .putExtra("release_date", movies.getReleaseDate())
//                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, holder.gmb, "imagetransition");
//                context.startActivity(intent, options.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
         private ImageView gmb;
        private CardView cv;
        private TextView judul, tgl, desc;

        ViewHolder(View itemView) {
            super(itemView);

            cv = itemView.findViewById(R.id.card_view);
            gmb = itemView.findViewById(R.id.movie_poster);
            judul = itemView.findViewById(R.id.movie_name);
            desc = itemView.findViewById(R.id.movie_desc);
            tgl = itemView.findViewById(R.id.movie_date);
        }
    }

}
