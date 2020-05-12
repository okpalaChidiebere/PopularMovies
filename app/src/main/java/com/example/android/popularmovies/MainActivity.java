package com.example.android.popularmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.popularmovies.database.MovieViewModel;
import com.example.android.popularmovies.model.Movies;
import com.example.android.popularmovies.sync.MovieSyncViewModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private  MovieAdapter mMoviesAdapter;

    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecyclerView;

    /*Puts extra name strings*/
    private static final String EXTRA_TITLE = "MOVIE_TITLE";
    private static final String EXTRA_POSTER_PATH = "MOVIE_THUMBNAIL";
    private static final String EXTRA_OVERVIEW = "MOVIE_OVERVIEW";
    private static final String EXTRA_VOTE_AVERAGE = "MOVIE_RATINGS";
    private static final String EXTRA_RELEASE_DATE = "MOVIE_RELEASE_DATE";
    private static final String EXTRA_MOVIE_ID = "MOVIE_ID";

    private static final int FORECAST_LOADER_ID = 0;

    private MovieViewModel mMovieViewModel;

    private MovieSyncViewModel mMovieSyncViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        int loaderId = FORECAST_LOADER_ID;

        List<Movies> movies = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

         mMoviesAdapter = new MovieAdapter(movies, this, this);

        mRecyclerView.setAdapter(mMoviesAdapter);

        mMovieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        mMovieSyncViewModel = new ViewModelProvider(this).get(MovieSyncViewModel.class);

        mMovieSyncViewModel.GetData(this);

        mMovieSyncViewModel.getOutputWorkInfo().observe(this, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfos) {

                // If there are no matching work info, do nothing
                if (workInfos == null || workInfos.isEmpty()) {
                    return;
                }

                // We only care about the first output status.
                // Every continuation has only one worker tagged TAG_SYNC_DATA
                WorkInfo workInfo = workInfos.get(0);
                Log.i(LOG_TAG, "WorkState: " + workInfo.getState());
                if (workInfo.getState() == WorkInfo.State.ENQUEUED) {
                    showWorkFinished();
                    retrieveMovies();
                }else {
                    showWorkInProgress();
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            openSettingsActivity();
            return true;
        }
        if (id == R.id.action_favourites) {
            openFavouriteActivity();
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

    private void openSettingsActivity(){
        Context context = this;
        Class destinationClass = SettingsActivity.class;
        Intent intentToStartSettingsActivity = new Intent(context, destinationClass);
        startActivity(intentToStartSettingsActivity);
    }

    private void openFavouriteActivity(){
        Context context = this;
        Class destinationClass = FavouriteActivity.class;
        Intent intentToStartSettingsActivity = new Intent(context, destinationClass);
        startActivity(intentToStartSettingsActivity);
    }

    private void retrieveMovies() {

        mMovieViewModel.getUsers(this).observe(this, new Observer<List<Movies>>() {
            @Override
            public void onChanged(@Nullable List<Movies> moviesList) {
                Log.d(LOG_TAG, "Updating List of movies from LiveData in ViewModel");
                //mMoviesAdapter.setData(movies);
                mMoviesAdapter.setData(moviesList);
            }
        });
    }

    private void showWorkInProgress() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void showWorkFinished() {
        mLoadingIndicator.setVisibility(View.GONE);
    }


}
