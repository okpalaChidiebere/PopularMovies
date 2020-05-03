package com.example.android.popularmovies.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "review_desc",
        foreignKeys = @ForeignKey(entity = Movie_Desc.class,
                parentColumns = "movie_id",
                childColumns = "movie_id",
                onDelete = ForeignKey.CASCADE))
public class Review_Desc {
    @PrimaryKey(autoGenerate = true)
    private int review_id;
    private int movie_id;
    private String author;
    private String content;


    public Review_Desc (int tReviewID, int tMovieID, String tAuthor, String tContent){
        review_id = tReviewID;
        movie_id = tMovieID;
        author = tAuthor;
        content = tContent;
    }

    @Ignore
    public Review_Desc (int tMovieID, String tAuthor, String tContent){
        movie_id = tMovieID;
        author = tAuthor;
        content = tContent;
    }

    // getters and setters are ignored for brevity but they are required for Room to work.
    public int getReviewID(){
        return review_id;
    }

    public int getMovieID(){
        return movie_id;
    }

    public String getAuthor(){
        return author;
    }

    public String getContent(){
        return content;
    }

    public void setReviewID(int tId){
        review_id = tId;
    }

    public void setMovieId(int tId){
        movie_id = tId;
    }

    public void setAuthor(String tAuthor){
        author = tAuthor;
    }

    public void setContent(String tContent){
        content = tContent;
    }

}
