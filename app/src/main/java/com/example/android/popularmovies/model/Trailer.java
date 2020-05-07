package com.example.android.popularmovies.model;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "trailer",
        foreignKeys = @ForeignKey(entity = Movies.class,
                parentColumns = "movie_id",
                childColumns = "movie_id",
                onDelete = ForeignKey.CASCADE))
public class Trailer {

    @PrimaryKey(autoGenerate = true)
    private int trailer_id;
    private int movie_id;
    private String thumbnail;
    private String name;


    /**
     * No args constructor for use in serialization
     */
    public Trailer() {
    }

    public Trailer (int tTrailerID, int tMovieID, String tName, String tThumbnail){
        this.trailer_id = tTrailerID;
        this.movie_id = tMovieID;
        this.thumbnail = tThumbnail;
        this.name = tName;
    }

    public Trailer (int tMovieID, String tName, String tThumbnail){
        this.movie_id = tMovieID;
        this.name = tName;
        this.thumbnail = tThumbnail;
    }

    @Ignore
    public Trailer (String tName, String tThumbnail){
        this.name = tName;
        this.thumbnail = tThumbnail;
    }

    // getters and setters are ignored for brevity but they are required for Room to work.
    public int getTrailer_id(){
        return trailer_id;
    }

    public int getMovie_id(){
        return movie_id;
    }

    public String getThumbnail(){
        return thumbnail;
    }

    public void setTrailer_id(int tId){
        this.trailer_id = tId;
    }

    public void setMovie_id(int tId){
        this.movie_id = tId;
    }

    public void setThumbnail(String video){
        this.thumbnail = video;
    }

    public void setName(String tName){
        this.name = tName;
    }

    public String getName(){
        return name;
    }


}
