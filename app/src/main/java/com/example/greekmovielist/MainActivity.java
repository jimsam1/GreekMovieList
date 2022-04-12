package com.example.greekmovielist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button nextPageButton, addMovieButton;
    EditText addName, addPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextPageButton = findViewById(R.id.nextPageBtn);
        addMovieButton = findViewById(R.id.addNewMovie);
        addName = findViewById(R.id.addName);
        addPlot = findViewById(R.id.addPlot);
    }

    public void nextPage (View view){
        Intent i = new Intent(this, MainActivity2.class);
        //Pass data to the SayHelloNewScreen Activity through the Intent

        //Ask Android to start the new Activity
        startActivity(i);
    }

    public void addMovie (View view){
        Movie movie;
        try {
            movie = new Movie(-1, addName.getText().toString(),addPlot.getText().toString());
            Toast.makeText(MainActivity.this, movie.toString(), Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(MainActivity.this, "error creating movie", Toast.LENGTH_SHORT).show();
            movie = new Movie(-1, "error", "error");
        }

        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

        boolean success = dataBaseHelper.addOne(movie);

        Toast.makeText(MainActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();

    }




}