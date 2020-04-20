package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmovies.model.Movies;
import com.example.android.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private  MovieAdapter mMoviesAdapter;

    private static final String BASE_MOVIEdb_REQUEST_URL =
            "https://api.themoviedb.org/3/discover/movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String tempMovie_dbURL = buildUrl(BASE_MOVIEdb_REQUEST_URL);
        loadMovieData(tempMovie_dbURL);

        List<Movies> movies = new ArrayList<>();

        RecyclerView  mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

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
        intentToStartDetailActivity.putExtra("MOVIE_TITLE", movie.getTitle());
        intentToStartDetailActivity.putExtra("MOVIE_THUMBNAIL", movie.getThumbnail());
        intentToStartDetailActivity.putExtra("MOVIE_OVERVIEW", movie.getOverView());
        intentToStartDetailActivity.putExtra("MOVIE_RATINGS", movie.getUserRatings());
        intentToStartDetailActivity.putExtra("MOVIE_RELEASE_DATE", movie.getReleaseDate());

        startActivity(intentToStartDetailActivity);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<Movies>> {
        @Override
        protected List<Movies> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            // Create a list of movies.
            List<Movies> result = NetworkUtils.fetchEarthquakeData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Movies> movies) {

            mMoviesAdapter.clear();

            if (movies != null && !movies.isEmpty()) {
                mMoviesAdapter.setData(movies);
            }
        }
    }

    private void loadMovieData(String theMoviedb_url) {
        new FetchMoviesTask().execute(theMoviedb_url);
    }



    public String buildUrl(String locationQuery) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Update URI to Use the User’s Preferred Sort Order
        String sortBy  = sharedPrefs.getString(
                getString(R.string.settings_sort_key),
                getString(R.string.settings_sort_default_value)
        );

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(BASE_MOVIEdb_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. For example, the `api-key=someRandomNumber`
        uriBuilder.appendQueryParameter(getString(R.string.api_key), getString(R.string.api_key_value))
                .appendQueryParameter(getString(R.string.settings_sort_key), sortBy);

        //https://api.themoviedb.org/3/discover/movie?api_key=someNUmber&sort_by=popularity.desc
        return uriBuilder.toString();
    }

    private void openSettingsActivity(){
        Context context = this;
        Class destinationClass = SettingsActivity.class;
        Intent intentToStartSettingsActivity = new Intent(context, destinationClass);
        startActivity(intentToStartSettingsActivity);
    }
}
