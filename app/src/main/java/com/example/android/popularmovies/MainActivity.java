package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.android.popularmovies.model.Movies;
import com.example.android.popularmovies.utils.NetworkUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Movies> movies = NetworkUtils.parseMoviesJson();

        RecyclerView  mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        MovieAdapter mForecastAdapter = new MovieAdapter(movies, this);

        mRecyclerView.setAdapter(mForecastAdapter);
    }
}
