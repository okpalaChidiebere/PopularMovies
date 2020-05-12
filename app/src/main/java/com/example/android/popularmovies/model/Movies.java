package com.example.android.popularmovies.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movies {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    private long mMovieID;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "thumbnail")
    private String mThumbnail;

    @ColumnInfo(name = "overview")
    private String mOverView;

    @ColumnInfo(name = "rating")
    private int mUserRatings;

    @ColumnInfo(name = "release_date")
    private String mReleaseDate;

    @ColumnInfo(name = "api_movie_id")
    private long mAPIMovieID;

    /**
     * No args constructor for use in serialization
     */
    public Movies() {
    }


    public Movies (int movieID, String title, String thumbnail, String overView, int ratings, String releaseDate){
        this.mMovieID = movieID;
        this.mTitle = title;
        this.mThumbnail = thumbnail;
        this.mOverView = overView;
        this.mUserRatings = ratings;
        this.mReleaseDate = releaseDate;
    }

    @Ignore
    public Movies (String title, String thumbnail, String overView, int ratings, String releaseDate){
        this.mTitle = title;
        this.mThumbnail = thumbnail;
        this.mOverView = overView;
        this.mUserRatings = ratings;
        this.mReleaseDate = releaseDate;
    }

    @Ignore
    public Movies (String title, String thumbnail, String overView, int ratings, String releaseDate, long api_movie_id){
        this.mTitle = title;
        this.mThumbnail = thumbnail;
        this.mOverView = overView;
        this.mUserRatings = ratings;
        this.mReleaseDate = releaseDate;
        this.mAPIMovieID = api_movie_id;
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

    public void setTitle(String title){
        this.mTitle = title;
    }

    public void setThumbnail(String thumbnail){
        this.mThumbnail = thumbnail;
    }

    public void setOverView(String overView){
        this.mOverView = overView;
    }

    public void setUserRatings(int userRatings){
        this.mUserRatings = userRatings;
    }

    public void setReleaseDate(String releaseDate){
        this.mReleaseDate = releaseDate;
    }

    public long getMovieID(){
        return mMovieID;
    }

    public void setMovieID(long movieID){
        this.mMovieID = movieID;
    }

    public long getAPIMovieID(){
        return mAPIMovieID;
    }

    public void setAPIMovieID (long api_movieID){
        mAPIMovieID = api_movieID;
    }
}
