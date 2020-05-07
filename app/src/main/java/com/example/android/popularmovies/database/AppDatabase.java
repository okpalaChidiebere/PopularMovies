package com.example.android.popularmovies.database;

import android.content.Context;
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

    private static Context mContext;

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "popularmovies";
    private static AppDatabase sInstance;

    public static synchronized AppDatabase getInstance(final Context context) {

        mContext=context;

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

    public static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void>{
        private MovieDao movieDao;
        private ReviewDao reviewDao;
        private TrailerDao trailerDao;
        public PopulateDatabaseAsyncTask(AppDatabase db){
            movieDao = db.movieDao();
            reviewDao = db.reviewDao();
            trailerDao = db.trailerDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            String url = NetworkUtils.buildMovieUrl("popularity.desc");
            List<Movies> movies = NetworkUtils.fetchEarthquakeData(url);
            //Log.d(LOG_TAG, "test: " + movies.get(0).getTitle());

            for (int i = 0; i < movies.size(); i++) {
                long movieRowId = movieDao.insertMovie(movies.get(i));

                //Log.d(LOG_TAG, "MOVIE_ID: " + movieRowId);

                List<Trailer> trailers = NetworkUtils.fetchTrailersData(NetworkUtils.buildTrailerUrl(movies.get(i).getAPIMovieID()));
                for (int j = 0; j < trailers.size(); j++) {
                    Trailer trailer = new Trailer((int)movieRowId, trailers.get(j).getName(), trailers.get(j).getThumbnail());
                    trailerDao.insertTrailer(trailer);
                }

                List<Review> reviews = NetworkUtils.fetchReviewsData(NetworkUtils.buildReviewUrl(movies.get(i).getAPIMovieID()));
                for (int j = 0; j < reviews.size(); j++) {
                    Review review = new Review((int)movieRowId, reviews.get(j).getAuthor(), reviews.get(j).getContent());
                    reviewDao.insertReview(review);
                }
            }
            return null;
        }

    }




}
