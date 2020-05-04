package com.example.android.popularmovies.database;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popularmovies.model.Favourite;
import com.example.android.popularmovies.model.Movies;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.Trailer;
import com.example.android.popularmovies.utils.NetworkUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Movies.class, Trailer.class,
        Review.class, Favourite.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String BASE_MOVIEdb_REQUEST_URL =
            "https://api.themoviedb.org/3";

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "popularmovies";
    private static AppDatabase sInstance;

    public static synchronized AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                        //.fallbackToDestructiveMigration()
                        .addCallback(new RoomDatabase.Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);

                                Log.d(LOG_TAG, "EXecuting");
                                new PopulateDatabaseAsyncTask(sInstance).execute();


                            }
                        })
                        .build();
            }

        }

        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract MovieDao movieDao();
    public abstract ReviewDao reviewDao();
    public abstract TrailerDao trailerDao();
    public abstract FavouriteDao favouriteDao();


    /*private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsyncTask(sInstance).execute();
        }
    };*/

    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void>{
        private MovieDao movieDao;
        private PopulateDatabaseAsyncTask(AppDatabase db){
            movieDao = db.movieDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String url = NetworkUtils.buildMovieUrl("popularity.desc");
            List<Movies> movies = NetworkUtils.fetchEarthquakeData(url);
            //System.out.println("test: " + movies.get(0).getTitle());
            Log.d(LOG_TAG, "test: " + movies.get(0).getTitle());

            for (int i = 0; i < movies.size(); i++) {
                long rowId = movieDao.insertMovie(movies.get(i));

                Log.d(LOG_TAG, "MOVIE_ID: " + rowId);
            }
            return null;
        }
    }




}
