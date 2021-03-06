package com.example.greekmovielist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MovieDetailsActivity extends AppCompatActivity {

    public int movieid;
    public ImageView moviePoster;
    public TextView movieTitleText;
    public TextView movieDescriptionText;
    public TextView movieAttributesText;

    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        moviePoster = findViewById(R.id.moviePoster);
        movieTitleText = findViewById(R.id.movieTitleText);
        movieDescriptionText = findViewById(R.id.movieDescriptionText);
        movieAttributesText = findViewById(R.id.movieAttributesText);

        try {
            dataBaseHelper = new DataBaseHelper(MovieDetailsActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        movieid = getIntent().getIntExtra("movieid", 0);
        updateContent();
    }

    //change content of views in activity
    public void updateContent() {
        Movie selectedMovie = dataBaseHelper.getMovieById(movieid);

        movieTitleText.setText(selectedMovie.getTitle());
        movieDescriptionText.setText(selectedMovie.getDescription());
        moviePoster.setImageResource(getResources().getIdentifier(selectedMovie.getImageName(), "drawable", this.getPackageName()));

        String temptext = "";
        temptext += "Πρώτη προβολή: " + selectedMovie.getReleaseDate() + "\n";
        temptext += "Διάρκεια: " + selectedMovie.getDurationString() + " λεπτά\n";
        temptext += "Βασισμένο: " + selectedMovie.getBasedOn() + "\n";
        ArrayList<Contributor> contributors = selectedMovie.getContributors();
        for(Contributor c : contributors) {
            temptext += c.getRole() + ": " + c.getName() + "\n";
        }
        movieAttributesText.setText(temptext);
    }
}