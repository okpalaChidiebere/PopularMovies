package com.example.android.popularmovies.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "favourite",
        foreignKeys = @ForeignKey(entity = Movies.class,
                parentColumns = "movie_id",
                childColumns = "movie_id",
                onDelete = ForeignKey.CASCADE))
public class Favourite {
    @PrimaryKey(autoGenerate = true)
    private int favourite_id;
    private int movie_id;

    /**
     * No args constructor for use in serialization
     */
    public Favourite() {
    }

    public Favourite(int tFavouriteID, int tMovieID){
        this.favourite_id = tFavouriteID;
        this.movie_id = tMovieID;
    }

    public Favourite(int tMovieID){
        this.movie_id = tMovieID;
    }

    // getters and setters are ignored for brevity but they are required for Room to work.
    public int getFavourite_id(){
        return favourite_id;
    }

    public int getMovie_id(){
        return movie_id;
    }


    public void setFavourite_id(int tId){
        this.favourite_id = tId;
    }

    public void setMovie_id(int tId){
        this.movie_id = tId;
    }

}
