package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.model.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private List<Movies> movies;
    private Context context;

    public MovieAdapter(List<Movies> movies){
        this.movies = movies;
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder{

        public final ImageView mMovieThumbnail;
        public final TextView mMovieTitle;

        public MovieAdapterViewHolder(View view){
            super(view);

            mMovieThumbnail = (ImageView) view.findViewById(R.id.tv_thumbnail);
            mMovieTitle = (TextView) view.findViewById(R.id.tv_movie_title);
        }

    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movies_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {

        Picasso.with(context)
                .load(movies.get(position).getThumbnail())
                .into(holder.mMovieThumbnail);
        holder.mMovieTitle.setText(movies.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
