package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.popularmovies.model.Movies;
import com.example.android.popularmovies.utils.NetworkUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Movies> movies = NetworkUtils.parseMoviesJson();

        RecyclerView  mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        MovieAdapter mForecastAdapter = new MovieAdapter(movies, this, this);

        mRecyclerView.setAdapter(mForecastAdapter);
    }

    @Override
    public void onClick(Movies movie) {

        Context context = this;

        Class destinationClass = MovieDetails.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("MOVIE_TITLE", movie.getTitle());
        intentToStartDetailActivity.putExtra("MOVIE_THUMBNAIL", movie.getThumbnail());
        intentToStartDetailActivity.putExtra("MOVIE_OVERVIEW", movie.getOverView());
        intentToStartDetailActivity.putExtra("MOVIE_RATINGS", movie.getUserRatings());
        intentToStartDetailActivity.putExtra("MOVIE_RELEASE_DATE", movie.getReleaseDate());

        startActivity(intentToStartDetailActivity);
    }
}
