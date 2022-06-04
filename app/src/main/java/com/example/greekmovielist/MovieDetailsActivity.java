package com.example.greekmovielist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MovieDetailsActivity extends AppCompatActivity {

    public int movieid = 0; //temporary default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
    }

    public void updateContent(int movieid) {

        this.movieid = movieid;
    }
}