package com.example.android.popularmovies.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Movies;
import com.example.android.popularmovies.utils.NetworkUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Movie_Desc.class, Trailer_Desc.class,
        Review_Desc.class, Favourite.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String BASE_MOVIEdb_REQUEST_URL =
            "https://api.themoviedb.org/3/discover/movie";

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "popularmovies";
    private static AppDatabase sInstance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                        //.fallbackToDestructiveMigration()
                        .build();
            }

        }

        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract Movie_DescDao movieDescDao();
    public abstract Review_DescDao reviewDescDao();
    public abstract Trailer_DescDao trailerDescDao();
    public abstract FavouriteDao favouriteDao();


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void>{
        private  Movie_DescDao movieDescDao;
        private PopulateDatabaseAsyncTask(AppDatabase db){
            movieDescDao = db.movieDescDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String url = buildUrl(BASE_MOVIEdb_REQUEST_URL);

            List<Movies> movies = NetworkUtils.fetchEarthquakeData(url);
            return null;
        }
    }


    public static String buildUrl(String locationQuery) {

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(locationQuery);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. For example, the `api-key=someRandomNumber`
        uriBuilder.appendQueryParameter("api_key", "3bc5452a3c521649c0b418627ea7ddab")
                .appendQueryParameter("sort_by", "popularity.desc");

        //https://api.themoviedb.org/3/discover/movie?api_key=someNUmber&sort_by=popularity.desc
        return uriBuilder.toString();
    }

}
