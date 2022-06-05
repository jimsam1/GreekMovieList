package com.example.greekmovielist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button nextPage1Button, addMovieButton, nextPage2Button;
    EditText addMovieid;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextPage1Button = findViewById(R.id.nextPage1Btn);
        nextPage2Button = findViewById(R.id.nextPage2Btn);
        addMovieButton = findViewById(R.id.showMovie);
        addMovieid = findViewById(R.id.addMovieid);

        nextPage2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextPage2(view);
            }
        });

        try {
            dataBaseHelper = new DataBaseHelper(MainActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nextPage1 (View view){
        Intent i = new Intent(this, MainActivity2.class);
        //Pass data to the SayHelloNewScreen Activity through the Intent

        //Ask Android to start the new Activity
        startActivity(i);
    }

    public void nextPage2(View view){
        Intent i = new Intent(this, MainActivity3.class);

        startActivity(i);
    }

    public void showMovie (View view) {
        Intent i = new Intent(this, MovieDetailsActivity.class);
        i.putExtra("movieid", Integer.valueOf(String.valueOf(addMovieid.getText())));
        startActivity(i);
    }

//    public void addMovie (View view){
//        Movie movie;
//        try {
//            movie = new Movie(-1, addName.getText().toString(),addMovieid.getText().toString());
//            Toast.makeText(MainActivity.this, movie.toString(), Toast.LENGTH_SHORT).show();
//        }catch(Exception e){
//            Toast.makeText(MainActivity.this, "error creating movie", Toast.LENGTH_SHORT).show();
//            movie = new Movie(-1, "error", "error");
//        }
//
//        //boolean success = dataBaseHelper.addOne(movie);
//
//        //Toast.makeText(MainActivity.this, "Success= " + success, Toast.LENGTH_SHORT).show();
//
//    }




}