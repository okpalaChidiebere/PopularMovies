package com.example.android.popularmovies.model;

public class Movies {

    private String mTitle;
    private String mThumbnail;
    private String mOverView;
    private String mUserRatings;
    private String mReleaseDate;

    /**
     * No args constructor for use in serialization
     */
    public Movies() {
    }

    public Movies (String title, String thumbnail, String overView, String ratings, String releaseDate){
        mTitle = title;
        mThumbnail = thumbnail;
        mOverView = overView;
        mUserRatings = ratings;
        mReleaseDate = releaseDate;
    }

    String getTitle(){
        return mTitle;
    }

    String getThumbnail(){
        return mThumbnail;
    }

    String getOverView(){
        return mOverView;
    }

    String getUserRatings(){
        return mUserRatings;
    }

    String getReleaseDate(){
        return mReleaseDate;
    }


}
