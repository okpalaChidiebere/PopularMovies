package com.example.android.popularmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.model.Movies;
import com.example.android.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<List<Movies>> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();



    private  MovieAdapter mMoviesAdapter;

    private static final String BASE_MOVIEdb_REQUEST_URL =
            "https://api.themoviedb.org/3/discover/movie";

    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecyclerView;

    /*Puts extra name strings*/
    private static final String EXTRA_TITLE = "MOVIE_TITLE";
    private static final String EXTRA_POSTER_PATH = "MOVIE_THUMBNAIL";
    private static final String EXTRA_OVERVIEW = "MOVIE_OVERVIEW";
    private static final String EXTRA_VOTE_AVERAGE = "MOVIE_RATINGS";
    private static final String EXTRA_RELEASE_DATE = "MOVIE_RELEASE_DATE";

    private static final int FORECAST_LOADER_ID = 0;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getInstance(getApplicationContext());

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        int loaderId = FORECAST_LOADER_ID;
        LoaderManager.LoaderCallbacks<List<Movies>> callback = MainActivity.this;
        Bundle bundleForLoader = null;
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(loaderId, bundleForLoader, callback);

        List<Movies> movies = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

         mMoviesAdapter = new MovieAdapter(movies, this, this);

        mRecyclerView.setAdapter(mMoviesAdapter);
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

        startActivity(intentToStartDetailActivity);
    }

    @NonNull
    @Override
    public Loader<List<Movies>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<List<Movies>>(this) {

            /* This String array will hold and help cache our weather data */
            List<Movies> mMovies = null;

            @Override
            protected void onStartLoading() {

                mMovies=mDb.movieDao().getAllMovies();

                if (mMovies.size()>0) {
                    deliverResult(mMovies);
                    Log.d(LOG_TAG, "exists"+ mMovies.size());
                } else {
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                    Log.d(LOG_TAG, "forceLoad");
                }
            }

            @Nullable
            @Override
            public List<Movies> loadInBackground() {

                String url = NetworkUtils.buildMovieUrl(getSortPreference());
                // Perform the network request, parse the response, and extract a list of earthquakes.
                List<Movies> movies = NetworkUtils.fetchEarthquakeData(url);

                return movies;
            }

            /*The LoaderManager initialized are designed to reload if the user navigates away from the
            activity and them returns. We can avoid the extra load if we don't find it desirable by
            caching and redelivering our existing result.*/
            public void deliverResult(List<Movies> data) {
                mMovies = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movies>> loader, List<Movies> data) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (data != null && !data.isEmpty()) {
            mMoviesAdapter.setData(data);
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movies>> loader) {
        // Loader reset, so we can clear out our existing data.
        mMoviesAdapter.clear();
    }



    public String getSortPreference(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Update URI to Use the User’s Preferred Sort Order
        String sortBy  = sharedPrefs.getString(
                getString(R.string.settings_sort_key),
                getString(R.string.settings_sort_default_value)
        );

        return sortBy;

    }

    private void openSettingsActivity(){
        Context context = this;
        Class destinationClass = SettingsActivity.class;
        Intent intentToStartSettingsActivity = new Intent(context, destinationClass);
        startActivity(intentToStartSettingsActivity);
    }
}
