package com.example.greekmovielist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.IOException;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ListView lv_movieList;
//    String[] name = {"Μήτσος", "Μητρούσης","Δημήτρης", "Τάκης", "Μητσάκος","Δημητράκης","Μητσούκος","Μήτσουλας","Μητσάκος","Μήτσους","Μητσούλης"};
//    String[] emptyName = {};
    String[] movieTitles;
    Button allMoviesButton;

    DataBaseHelper dataBaseHelper;
    private ArrayAdapter<String> arrayAdapter;
//    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        allMoviesButton = findViewById(R.id.allMoviesBtn);

        allMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allMoviesPage(view);
            }
        });


        try {
            dataBaseHelper = new DataBaseHelper(HomeActivity.this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Movie> movies = dataBaseHelper.getAllMovies();


        int size = movies.size();
        movieTitles = new String[size];

        for(int i=0; i<movies.size(); i++) {
            movieTitles[i] = (movies.get(i).getTitle());
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,movieTitles);

        lv_movieList = findViewById(R.id.lv_movieList);
        lv_movieList.setAdapter(arrayAdapter);

    }

    public void allMoviesPage(View view){
        Intent i = new Intent(this, MainActivity3.class);

        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.nav_menu, menu);


        MenuItem menuitem = menu.findItem(R.id.nav_search);
        SearchView searchView = (SearchView) menuitem.getActionView();
        searchView.setQueryHint("Αναζήτησε ταινίες εδώ!");
        lv_movieList.setVisibility(View.INVISIBLE);
        Intent i = new Intent(this,MainActivity3.class);

        final String[]searchResults = new String[10];

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query){
                lv_movieList.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(query)){
                    lv_movieList.clearTextFilter();
                }else{
                    lv_movieList.setFilterText(query.toString());
                }
                i.putExtra("inputQuery", query);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText){
                lv_movieList.setVisibility(View.VISIBLE);
                if (newText.isEmpty()) {
                    lv_movieList.clearTextFilter();
                    lv_movieList.setVisibility(View.INVISIBLE);
                }

                arrayAdapter.getFilter().filter(newText);

                return false;
            }
        });

        Intent i2 = new Intent(this,MovieDetailsActivity.class);


        lv_movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            String title;

            public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {

                title = parentAdapter.getItemAtPosition(position).toString();
                int movieId = dataBaseHelper.getMovieidByTitle(title);
//                Log.d("YOLO",Integer.toString((int) id));
                i2.putExtra("movieid", movieId);
                startActivity(i2);

            }
        });



        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose(){
                lv_movieList.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}