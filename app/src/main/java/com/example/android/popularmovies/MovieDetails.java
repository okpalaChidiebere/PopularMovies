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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.database.GetTrailerViewModel;
import com.example.android.popularmovies.database.GetTrailersViewModelFactory;
import com.example.android.popularmovies.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieDetails extends AppCompatActivity {

    private static final String LOG_TAG = MovieDetails.class.getSimpleName();


    /*Intent extra names*/
    private static final String EXTRA_TITLE = "MOVIE_TITLE";
    private static final String EXTRA_POSTER_PATH = "MOVIE_THUMBNAIL";
    private static final String EXTRA_OVERVIEW = "MOVIE_OVERVIEW";
    private static final String EXTRA_VOTE_AVERAGE = "MOVIE_RATINGS";
    private static final String EXTRA_RELEASE_DATE = "MOVIE_RELEASE_DATE";
    private static final String EXTRA_MOVIE_ID = "MOVIE_ID";

    private RecyclerView mTv_recyclerView_trailer;
    private  TrailerAdapter mTrailerAdapter;

    private GetTrailersViewModelFactory factory;
    private GetTrailerViewModel mTrailerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //layout views
        TextView titleView, descriptionView, releaseDateView, ratingsView;
        ImageView thumbnailView, insertImageView, thumbnailBackgroundView;

        //intent variables
        String mMovieTitle, mThumbnail, mOverview, releaseDate;
        int mRatings;
        long movieID;

        thumbnailBackgroundView = (ImageView) findViewById(R.id.tv_detail_thumbnail_background);
        titleView = (TextView) findViewById(R.id.tv_detail_title);
        thumbnailView = (ImageView) findViewById(R.id.tv_detail_thumbnail);
        insertImageView = (ImageView) findViewById(R.id.tv_detail_insertImage);
        descriptionView = (TextView) findViewById(R.id.tv_detail_description);
        ratingsView = (TextView) findViewById(R.id.tv_detail_ratings);
        releaseDateView = (TextView) findViewById(R.id.tv_detail_releaseDate);

        mTv_recyclerView_trailer = (RecyclerView) findViewById(R.id.tv_recyclerview_trailers);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTv_recyclerView_trailer.setLayoutManager(layoutManager);
        mTv_recyclerView_trailer.setHasFixedSize(true);
        mTrailerAdapter = new TrailerAdapter(this);

        mTv_recyclerView_trailer.setAdapter(mTrailerAdapter);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {

            if (intentThatStartedThisActivity.hasExtra(EXTRA_TITLE)) {
                mMovieTitle = intentThatStartedThisActivity.getStringExtra(EXTRA_TITLE);
                titleView.setText(mMovieTitle);
            }

            if (intentThatStartedThisActivity.hasExtra(EXTRA_POSTER_PATH)) {
                mThumbnail = intentThatStartedThisActivity.getStringExtra(EXTRA_POSTER_PATH);
                if(mThumbnail.contains("null")) {
                    thumbnailView.setVisibility(View.INVISIBLE);
                    insertImageView.setVisibility(View.VISIBLE);
                }else {
                    thumbnailView.setVisibility(View.VISIBLE);
                    insertImageView.setVisibility(View.INVISIBLE);
                    Picasso.with(this)
                            .load(mThumbnail)
                            .into(thumbnailView);
                }

                Picasso.with(this)
                        .load(mThumbnail)
                        .into(thumbnailBackgroundView);
            }

            if (intentThatStartedThisActivity.hasExtra(EXTRA_OVERVIEW)) {
                mOverview = intentThatStartedThisActivity.getStringExtra(EXTRA_OVERVIEW);
                descriptionView.setText(mOverview);
            }

            if (intentThatStartedThisActivity.hasExtra(EXTRA_VOTE_AVERAGE)) {
                mRatings = intentThatStartedThisActivity.getIntExtra(EXTRA_VOTE_AVERAGE,0);
                ratingsView.setText(String.valueOf(mRatings)+"/10");
            }

            if (intentThatStartedThisActivity.hasExtra(EXTRA_RELEASE_DATE)) {
                releaseDate = intentThatStartedThisActivity.getStringExtra(EXTRA_RELEASE_DATE);
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
                //Date dt_1 = sdf.parse(releaseDate);
                //Date dt_1 = new Date(releaseDate);
                //releaseDateView.setText(sdf.format(releaseDate));
                releaseDateView.setText(releaseDate);
            }

            if (intentThatStartedThisActivity.hasExtra(EXTRA_MOVIE_ID)) {
                movieID = intentThatStartedThisActivity.getLongExtra(EXTRA_MOVIE_ID,0);
                factory = new GetTrailersViewModelFactory(this, (int)movieID);
                loadMovieReviews();
            }

        }

    }

    public void loadMovieReviews(){
        mTrailerViewModel =
                new ViewModelProvider(this, factory).get(GetTrailerViewModel.class);

        mTrailerViewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(@Nullable List<Trailer> trailerList) {
                Log.d(LOG_TAG, "Updating List of trailers from LiveData in ViewModel");
                mTrailerAdapter.setData(trailerList);
            }
        });
    }
}
