package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.android.popularmovies.database.AppDatabase;
import com.example.android.popularmovies.model.Review;

import java.util.List;

public class MovieReviews extends AppCompatActivity {

    private static final String LOG_TAG = MovieReviews.class.getSimpleName();
    private static final String EXTRA_MOVIE_ID = "MOVIE_ID";

    private RecyclerView mTv_recyclerView_review;
    private ReviewAdapter mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_reviews);

        int mMovieID;

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.action_movieReview)); //page label

        mTv_recyclerView_review = (RecyclerView) findViewById(R.id.tv_recyclerView_moviesReviews);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTv_recyclerView_review.setLayoutManager(layoutManager);
        mTv_recyclerView_review.setHasFixedSize(true);
        mReviewAdapter = new ReviewAdapter(this);

        mTv_recyclerView_review.setAdapter(mReviewAdapter);


        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {

            if (intentThatStartedThisActivity.hasExtra(EXTRA_MOVIE_ID)) {
                mMovieID = intentThatStartedThisActivity.getIntExtra(EXTRA_MOVIE_ID,0);
                Log.d(LOG_TAG, "MovieID for reviews: "+ mMovieID);

                //TODO (1) Testing on UI thread the setAdapter. Must change it to view Model*/
                List<Review> mReviews= AppDatabase.getInstance(this).reviewDao().getAllReviewByMovieID((int)mMovieID);
                Log.d(LOG_TAG, "Number of Reviews" + mReviews.size());
                mReviewAdapter.setData(mReviews);

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
