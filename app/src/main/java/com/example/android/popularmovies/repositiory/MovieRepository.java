package com.example.android.popularmovies.repositiory;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.database.FavouriteDao;
import com.example.android.popularmovies.database.MovieDao;
import com.example.android.popularmovies.database.ReviewDao;
import com.example.android.popularmovies.database.TrailerDao;
import com.example.android.popularmovies.model.Favourite;
import com.example.android.popularmovies.model.Movies;
import com.example.android.popularmovies.model.Review;
import com.example.android.popularmovies.model.Trailer;
import com.example.android.popularmovies.utils.NetworkUtils;

import java.util.List;

import androidx.lifecycle.LiveData;

public class MovieRepository {

    private MovieDao mMovieDao;
    private ReviewDao mReviewDao;
    private TrailerDao mTrailerDao;
    private LiveData<List<Trailer>> mTrailers;
    private static long mMovieid;
    private FavouriteDao mFavouriteDao;


    private static final String LOG_TAG = MovieRepository.class.getSimpleName();


    private static Context mContext;

    public MovieRepository(Context context) {
        mContext = context;
        AppDatabase db = AppDatabase.getInstance(context);
        mMovieDao = db.movieDao();
        mReviewDao = db.reviewDao();
        mTrailerDao = db.trailerDao();
        mFavouriteDao = db.favouriteDao();
    }

    public void rePopulateTables(String sortMovie) {
        new rePopulateDatabaseAsyncTask(mMovieDao, mTrailerDao, mReviewDao, mFavouriteDao, sortMovie).execute();
    }

    private static class rePopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

        private MovieDao mMovieDao;
        private ReviewDao mReviewDao;
        private TrailerDao mTrailerDao;
        private String sortMovieValue;
        private FavouriteDao mFavouriteDao;
        List<Review> reviews;
        List<Trailer> trailers;

        //rePopulateDatabaseAsyncTask() {
        rePopulateDatabaseAsyncTask(MovieDao dao, TrailerDao tDao, ReviewDao rDao, FavouriteDao fDao, String sortMovie) {
            mMovieDao = dao;
            mReviewDao = rDao;
            mTrailerDao = tDao;
            sortMovieValue = sortMovie;
            mFavouriteDao = fDao;
        }

        @Override
        protected Void doInBackground(final Void... voids) {

            mMovieDao.deleteAll();
            mReviewDao.deleteAll();
            mTrailerDao.deleteAll();
            mFavouriteDao.deleteAll();

            String url = NetworkUtils.buildMovieUrl(sortMovieValue);
            List<Movies> movies = NetworkUtils.fetchEarthquakeData(url);

            for (int i = 0; i < movies.size(); i++) {
                long movieRowId = mMovieDao.insertMovie(movies.get(i));
                Log.d(LOG_TAG, "MOVIE_ID: " + movieRowId);

                trailers = NetworkUtils.fetchTrailersData(NetworkUtils.buildTrailerUrl(movies.get(i).getAPIMovieID()));
                for (int j = 0; j < trailers.size(); j++) {
                    Trailer trailer = new Trailer((int)movieRowId, trailers.get(j).getName(), trailers.get(j).getThumbnail());
                    mTrailerDao.insertTrailer(trailer);
                }

                reviews = NetworkUtils.fetchReviewsData(NetworkUtils.buildReviewUrl(movies.get(i).getAPIMovieID()));
                for (int j = 0; j < reviews.size(); j++) {
                    Review review = new Review((int)movieRowId, reviews.get(j).getAuthor(), reviews.get(j).getContent());
                    mReviewDao.insertReview(review);
                }

            }
            return null;
        }
    }

    public void addMovieToFavourites(int movieID){

        new FavouriteAsyncTask(mFavouriteDao, movieID, true).execute();
    }

    public void removeMovieToFavourites(int movieID){

        new FavouriteAsyncTask(mFavouriteDao, movieID, false).execute();
    }



    private static class FavouriteAsyncTask extends AsyncTask<Void, Void, Void> {

        private FavouriteDao mFavouriteDao;
        private int mMovieID;
        private boolean mIfAdd;

        FavouriteAsyncTask(FavouriteDao dao, int mID, boolean ifAdd){
            mFavouriteDao = dao;
            mMovieID = mID;
            mIfAdd = ifAdd;
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            if(mIfAdd) {
                Favourite favourite = new Favourite(mMovieID);
                mFavouriteDao.insertFavourite(favourite);
            }else{
                mFavouriteDao.deleteFavouriteByMovieId(mMovieID);
            }


            return null;
        }

    }

    public LiveData<Integer> checkMovieFavourites(int movieID) {
        return mFavouriteDao.getFavouriteById(movieID);
    }


}
