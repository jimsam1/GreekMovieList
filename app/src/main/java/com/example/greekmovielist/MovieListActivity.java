package com.example.greekmovielist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder> adapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        recyclerView = findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try {
            dataBaseHelper = new DataBaseHelper(MovieListActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List movies = null;
        String intentQuery = getIntent().getStringExtra("inputQuery");
        if(intentQuery == null) {
            movies = dataBaseHelper.getAllMovies();
        } else {
            movies = dataBaseHelper.getMovieListByQuery(intentQuery);
        }


        adapter = new RecyclerAdapter(movies, this);
        recyclerView.setAdapter(adapter);
    }


}
