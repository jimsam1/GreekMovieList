package com.example.greekmovielist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    Button nextPageButton;

    public void nextPage (View view){
        Intent i = new Intent(this, MainActivity2.class);
//Pass data to the SayHelloNewScreen Activity through the Intent
        nextPageButton = findViewById(R.id.nextPageBtn);
//Ask Android to start the new Activity
        startActivity(i);
    }


}