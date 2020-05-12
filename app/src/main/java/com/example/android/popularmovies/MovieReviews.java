package com.example.android.popularmovies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.popularmovies.database.GetMovieIdViewModelFactory;
import com.example.android.popularmovies.database.GetReviewViewModel;
import com.example.android.popularmovies.model.Review;

import org.w3c.dom.Text;

import java.util.List;

public class MovieReviews extends AppCompatActivity {

    private static final String LOG_TAG = MovieReviews.class.getSimpleName();
    private static final String EXTRA_MOVIE_ID = "MOVIE_ID";

    private RecyclerView mTv_recyclerView_review;
    private ReviewAdapter mReviewAdapter;

    private GetMovieIdViewModelFactory factory;
    private GetReviewViewModel mReviewViewModel;

    private TextView defaultMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_reviews);

        int mMovieID;

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.action_movieReview)); //page label

        mTv_recyclerView_review = (RecyclerView) findViewById(R.id.tv_recyclerView_moviesReviews);
        defaultMessage = (TextView) findViewById(R.id.default_review_message);
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

                factory = new GetMovieIdViewModelFactory(this, mMovieID, true, false);
                loadMovieReviews();

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

    public void loadMovieReviews(){
        mReviewViewModel =
                new ViewModelProvider(this, factory).get(GetReviewViewModel.class);

        mReviewViewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviewList) {
                Log.d(LOG_TAG, "Updating List of Reviews from LiveData in ViewModel");
                if(reviewList.size() != 0) {
                    MovieReviewListExists();
                    mReviewAdapter.setData(reviewList);
                }else{
                            noMovieReviewList();
                }
            }
        });
    }

    private void MovieReviewListExists() {
        defaultMessage.setVisibility(View.GONE);
        mTv_recyclerView_review.setVisibility(View.VISIBLE);
    }

    private void noMovieReviewList(){
        defaultMessage.setVisibility(View.VISIBLE);
        mTv_recyclerView_review.setVisibility(View.GONE);
    }
}
