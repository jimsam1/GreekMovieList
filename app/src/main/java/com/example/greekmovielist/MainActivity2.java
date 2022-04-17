package com.example.greekmovielist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    ListView lv_movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        lv_movieList = findViewById(R.id.lv_movieList);
    }

    public void viewAll(View view) {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity2.this);
        List<Movie> allMovies = dataBaseHelper.getAllMovies();

        ArrayAdapter movieArrayAdapter = new ArrayAdapter<Movie>(MainActivity2.this, android.R.layout.simple_list_item_1, allMovies);
        lv_movieList.setAdapter(movieArrayAdapter);

        //Toast.makeText(MainActivity2.this, allMovies.toString(), Toast.LENGTH_SHORT).show();
    }
}