package com.example.android.popularmovies.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "trailer_desc",
        foreignKeys = @ForeignKey(entity = Movie_Desc.class,
        parentColumns = "movie_id",
        childColumns = "movie_id",
        onDelete = ForeignKey.CASCADE))
public class Trailer_Desc {
    @PrimaryKey(autoGenerate = true)
    private int trailer_id;
    private int movie_id;
    private String thumbnail;


    public Trailer_Desc (int tTrailerID, int tMovieID, String tThumbnail){
        trailer_id = tTrailerID;
        movie_id = tMovieID;
        thumbnail = tThumbnail;
    }

    @Ignore
    public Trailer_Desc (int tMovieID, String tThumbnail){
        movie_id = tMovieID;
        thumbnail = tThumbnail;
    }

    // getters and setters are ignored for brevity but they are required for Room to work.
    public int getTrailerID(){
        return trailer_id;
    }

    public int getMovieID(){
        return movie_id;
    }

    public String getThumbnail(){
        return thumbnail;
    }

    public void setTrailerID(int tId){
        trailer_id = tId;
    }

    public void setMovieId(int tId){
        movie_id = tId;
    }

    public void setThumbnail(String video){
        thumbnail = video;
    }

}
