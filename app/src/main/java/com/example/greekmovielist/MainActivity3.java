package com.example.greekmovielist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder> adapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        recyclerView = findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        try {
            dataBaseHelper = new DataBaseHelper(MainActivity3.this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List movies = dataBaseHelper.getAllMovies();
        //Log.i("Testing", String.valueOf(movies));

        adapter = new RecyclerAdapter(movies, this);
        recyclerView.setAdapter(adapter);
    }


}
