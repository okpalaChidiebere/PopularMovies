package com.example.android.popularmovies.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "movie_desc")
public class Movie_Desc {
        @PrimaryKey(autoGenerate = true)
        private int movie_id;
        private String title;
        private String thumbnail;
        private String overview;
        @ColumnInfo(name = "user_ratings")
        private int userRatings;
        @ColumnInfo(name = "release_date")
        private String releaseDate;

        public Movie_Desc(int tID, String tTitle, String tThumnail, String tOverview, int rating, String date){
               movie_id = tID;
               title = tTitle;
               thumbnail = tThumnail;
               overview = tOverview;
               userRatings = rating;
               releaseDate = date;
        }

        @Ignore
        public Movie_Desc(String tTitle, String tThumnail, String tOverview, int rating, String date){
                title = tTitle;
                thumbnail = tThumnail;
                overview = tOverview;
                userRatings = rating;
                releaseDate = date;
        }


        // getters and setters are ignored for brevity but they are required for Room to work.
        public int getId() {
                return movie_id;
        }

        public String getTitle(){
                return title;
        }

        public String getThumbnail(){
                return thumbnail;
        }

        public String getOverView(){
                return overview;
        }

        public int getUserRatings(){
                return userRatings;
        }

        public String getReleaseDate(){
                return releaseDate;
        }

        public void setId(int id) {
                movie_id = id;
        }

        public void setTitle(String t){
                title = t;
        }

        public void setThumbnail(String t){
                thumbnail = t;
        }

        public void setOverView(String o){
                overview = o;
        }

        public void setUserRatings(int u){
                userRatings = u;
        }

        public void setReleaseDate(String r){
                releaseDate = r;
        }
}


