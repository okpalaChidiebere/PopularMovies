package com.example.android.popularmovies.database;

import android.content.Context;
import android.util.Log;

import com.example.android.popularmovies.model.Favourite;
import com.example.android.popularmovies.model.Movies;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.Trailer;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

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
                        //.allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                        .fallbackToDestructiveMigration()
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

}
