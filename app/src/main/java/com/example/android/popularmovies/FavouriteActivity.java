package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.popularmovies.database.FavouriteViewModel;
import com.example.android.popularmovies.database.MovieViewModel;
import com.example.android.popularmovies.model.Movies;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    private static final String LOG_TAG = FavouriteActivity.class.getSimpleName();

    /*Puts extra name strings*/
    private static final String EXTRA_TITLE = "MOVIE_TITLE";
    private static final String EXTRA_POSTER_PATH = "MOVIE_THUMBNAIL";
    private static final String EXTRA_OVERVIEW = "MOVIE_OVERVIEW";
    private static final String EXTRA_VOTE_AVERAGE = "MOVIE_RATINGS";
    private static final String EXTRA_RELEASE_DATE = "MOVIE_RELEASE_DATE";
    private static final String EXTRA_MOVIE_ID = "MOVIE_ID";

    private RecyclerView mRecyclerView;
    private  MovieAdapter mMoviesAdapter;
    private FavouriteViewModel mFavouriteViewModel;

    private TextView defaultMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<Movies> movies = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_favourites);
        defaultMessage = (TextView) findViewById(R.id.default_movies_message);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mMoviesAdapter = new MovieAdapter(movies, this, this);

        mRecyclerView.setAdapter(mMoviesAdapter);

        mFavouriteViewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
        retrieveFavourites();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Movies movie) {

        Context context = this;
        Class destinationClass = MovieDetails.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(EXTRA_TITLE, movie.getTitle());
        intentToStartDetailActivity.putExtra(EXTRA_POSTER_PATH, movie.getThumbnail());
        intentToStartDetailActivity.putExtra(EXTRA_OVERVIEW, movie.getOverView());
        intentToStartDetailActivity.putExtra(EXTRA_VOTE_AVERAGE, movie.getUserRatings());
        intentToStartDetailActivity.putExtra(EXTRA_RELEASE_DATE, movie.getReleaseDate());
        intentToStartDetailActivity.putExtra(EXTRA_MOVIE_ID, movie.getMovieID());

        startActivity(intentToStartDetailActivity);
    }

    private void retrieveFavourites() {
        mFavouriteViewModel.getFavourites(this).observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(List<Movies> movies) {
                if(movies.size() != 0) {
                    favouriteMovieListExists();
                    mMoviesAdapter.setData(movies);
                }else{
                    noMovieFavouriteList();
                }
            }
        });
    }

    private void favouriteMovieListExists() {

        defaultMessage.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void noMovieFavouriteList(){
        defaultMessage.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

}
