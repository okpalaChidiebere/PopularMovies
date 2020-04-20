package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieDetails extends AppCompatActivity {

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

        thumbnailBackgroundView = (ImageView) findViewById(R.id.tv_detail_thumbnail_background);
        titleView = (TextView) findViewById(R.id.tv_detail_title);
        thumbnailView = (ImageView) findViewById(R.id.tv_detail_thumbnail);
        insertImageView = (ImageView) findViewById(R.id.tv_detail_insertImage);
        descriptionView = (TextView) findViewById(R.id.tv_detail_description);
        ratingsView = (TextView) findViewById(R.id.tv_detail_ratings);
        releaseDateView = (TextView) findViewById(R.id.tv_detail_releaseDate);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {

            if (intentThatStartedThisActivity.hasExtra("MOVIE_TITLE")) {
                mMovieTitle = intentThatStartedThisActivity.getStringExtra("MOVIE_TITLE");
                titleView.setText(mMovieTitle);
            }

            if (intentThatStartedThisActivity.hasExtra("MOVIE_THUMBNAIL")) {
                mThumbnail = intentThatStartedThisActivity.getStringExtra("MOVIE_THUMBNAIL");
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

            if (intentThatStartedThisActivity.hasExtra("MOVIE_OVERVIEW")) {
                mOverview = intentThatStartedThisActivity.getStringExtra("MOVIE_OVERVIEW");
                descriptionView.setText(mOverview);
            }

            if (intentThatStartedThisActivity.hasExtra("MOVIE_RATINGS")) {
                mRatings = intentThatStartedThisActivity.getIntExtra("MOVIE_RATINGS",0);
                ratingsView.setText(String.valueOf(mRatings)+"/10");
            }

            if (intentThatStartedThisActivity.hasExtra("MOVIE_RELEASE_DATE")) {
                releaseDate = intentThatStartedThisActivity.getStringExtra("MOVIE_RELEASE_DATE");
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
                //Date dt_1 = sdf.parse(releaseDate);
                //Date dt_1 = new Date(releaseDate);
                //releaseDateView.setText(sdf.format(releaseDate));
                releaseDateView.setText(releaseDate);
            }

        }
    }
}
