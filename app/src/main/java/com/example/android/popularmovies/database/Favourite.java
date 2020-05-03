package com.example.android.popularmovies.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "favourite",
        foreignKeys = @ForeignKey(entity = Movie_Desc.class,
                parentColumns = "movie_id",
                childColumns = "movie_id",
                onDelete = ForeignKey.CASCADE))
public class Favourite {
    @PrimaryKey(autoGenerate = true)
    private int favourite_id;
    private int movie_id;


    public Favourite (int tFavouriteID, int tMovieID){
        favourite_id = tFavouriteID;
        movie_id = tMovieID;
    }

    // getters and setters are ignored for brevity but they are required for Room to work.
    public int getFavouriteID(){
        return favourite_id;
    }

    public int getMovieID(){
        return movie_id;
    }


    public void setFavouriteID(int tId){
        favourite_id = tId;
    }

    public void setMovieId(int tId){
        movie_id = tId;
    }


}
