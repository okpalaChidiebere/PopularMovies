package com.example.android.popularmovies.model;

public class Movies {

    private String mTitle;
    private String mThumbnail;
    private String mOverView;
    private int mUserRatings;
    private String mReleaseDate;

    /**
     * No args constructor for use in serialization
     */
    public Movies() {
    }

    public Movies (String title, String thumbnail, String overView, int ratings, String releaseDate){
        mTitle = title;
        mThumbnail = thumbnail;
        mOverView = overView;
        mUserRatings = ratings;
        mReleaseDate = releaseDate;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getThumbnail(){
        return mThumbnail;
    }

    public String getOverView(){
        return mOverView;
    }

    public int getUserRatings(){
        return mUserRatings;
    }

    public String getReleaseDate(){
        return mReleaseDate;
    }


}
