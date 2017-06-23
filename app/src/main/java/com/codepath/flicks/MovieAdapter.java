package com.codepath.flicks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.flicks.models.Config;
import com.codepath.flicks.models.Movie;

import java.util.ArrayList;

/**
 * Created by brucegatete on 6/22/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    //list of movies
    ArrayList<Movie> movies;
    //config needed for image url
    Config config;
    //context for rendering
    Context context;
    //initialize the movie list
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    // creates and inflates new view
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //get the context and create the inflater
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //create the view using the item_movie layout
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        // return a new ViewHolder
        return new ViewHolder(movieView);


    }
    //binds an inflated view to a new item
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    //get the movie data at the specified position
        Movie movie = movies.get(position);
        //populate the view with movie data
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());

        // - TODO set image
        //create an  url  for poster image
        String imageUrl =config.getImageUrl(config.getPosterSize(), movie.getPosterPath());
        Log.d("debug", "imageUrl is " + imageUrl);
        //load image using glide
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.flicks_movie_placeholder)
                .error(R.drawable.flicks_movie_placeholder)
                .into(holder.ivPosterImage);


    }
    // returns the total number of movies in the list

    @Override
    public int getItemCount() {
        return movies.size();
    }
//create the view holder as a static inner class

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // track view objects
        ImageView ivPosterImage;
        TextView tvTitle;
        TextView tvOverview;
        public ViewHolder(View itemView) {
            super(itemView);
            //lookup view objects by id
            ivPosterImage = (ImageView) itemView.findViewById(R.id.ivPosterImage);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);

        }
    }
}