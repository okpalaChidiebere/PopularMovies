package com.example.android.popularmovies.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "review",
        foreignKeys = @ForeignKey(entity = Movies.class,
                parentColumns = "movie_id",
                childColumns = "movie_id",
                onDelete = ForeignKey.CASCADE))
public class Review {

    @PrimaryKey(autoGenerate = true)
    private int review_id;
    private int movie_id;
    private String author;
    private String content;

    /**
     * No args constructor for use in serialization
     */
    public Review() {
    }

    public Review (int tReviewID, int tMovieID, String tAuthor, String tContent){
        this.review_id = tReviewID;
        this.movie_id = tMovieID;
        this.author = tAuthor;
        this.content = tContent;
    }

    @Ignore
    public Review (int tMovieID, String tAuthor, String tContent){
        this.movie_id = tMovieID;
        this.author = tAuthor;
        this.content = tContent;
    }

    @Ignore
    public Review (String tAuthor, String tContent){
        this.author = tAuthor;
        this.content = tContent;
    }

    // getters and setters are ignored for brevity but they are required for Room to work.
    public int getReview_id(){
        return review_id;
    }

    public int getMovie_id(){
        return movie_id;
    }

    public String getAuthor(){
        return author;
    }

    public String getContent(){
        return content;
    }

    public void setReview_id(int tId){
        this.review_id = tId;
    }

    public void setMovie_id(int tId){
        this.movie_id = tId;
    }

    public void setAuthor(String tAuthor){
        this.author = tAuthor;
    }

    public void setContent(String tContent){
        this.content = tContent;
    }



}
