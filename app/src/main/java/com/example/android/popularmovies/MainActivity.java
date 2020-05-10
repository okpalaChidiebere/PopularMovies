package com.example.android.popularmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.database.MovieViewModel;
import com.example.android.popularmovies.model.Movies;
import com.example.android.popularmovies.repositiory.MovieRepository;
import com.example.android.popularmovies.sync.MovieSyncUtils;
import com.example.android.popularmovies.sync.MovieSyncViewModel;
import com.example.android.popularmovies.utils.NetworkUtils;
import com.example.android.popularmovies.utils.NotificationUtils;

import java.util.ArrayList;
import java.util.List;

/*
* public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<List<Movies>>, SharedPreferences.OnSharedPreferenceChangeListener {*/

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
        /*LoaderManager.LoaderCallbacks<List<Movies>> callback = MainActivity.this;
        Bundle bundleForLoader = null;
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(loaderId, bundleForLoader, callback);*/

        List<Movies> movies = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

         mMoviesAdapter = new MovieAdapter(movies, this, this);

        mRecyclerView.setAdapter(mMoviesAdapter);

        mMovieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        //retrieveMovies();

        //MovieSyncUtils.initialize(this);

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
        //intentToStartDetailActivity.putExtra(EXTRA_MOVIE_ID, (int) movieID);
        intentToStartDetailActivity.putExtra(EXTRA_MOVIE_ID, movie.getMovieID());

        startActivity(intentToStartDetailActivity);
    }

    /*@NonNull
    @Override
    public Loader<List<Movies>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<List<Movies>>(this) {

            // This String array will hold and help cache our weather data
            LiveData<List<Movies>> mMovies = null;

            @Override
            protected void onStartLoading() {

                mMovies=mDb.movieDao().getAllMovies();

                if (mMovies != null) {
                    //deliverResult(mMovies);

                    retrieveMovies();
                    Log.d(LOG_TAG, "exists");
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

            //The LoaderManager initialized are designed to reload if the user navigates away from the
            //activity and them returns. We can avoid the extra load if we don't find it desirable by
            //caching and redelivering our existing result.
            public void deliverResult(List<Movies>  data) {
                mMovies = data;
                super.deliverResult(data);
            }
        };
    }*/

    /*@Override
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

    }*/

    private void openSettingsActivity(){
        Context context = this;
        Class destinationClass = SettingsActivity.class;
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

    /*private void observeWorkManager(OneTimeWorkRequest workRequest){
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {

                        //Displaying the status into TextView
                        //textView.append(workInfo.getState().name() + "\n");
                        String status = workInfo.getState().name();
                        if(status == "SUCCEEDED"){
                            NotificationUtils.notifyUserOfNewWeather(getApplicationContext());
                        }
                    }
                });
    }*/

}
