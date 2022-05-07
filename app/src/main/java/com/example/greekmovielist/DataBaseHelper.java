package com.example.greekmovielist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

//    Global μεταβλητές που τις χρησιμοποιώ συχνά και προτιμώ το autofill όταν τις γράφω για ευκολία
    public static final String MOVIE_TABLE = "movie";
    public static final String COL_MOVIE_NAME = "title";
    public static final String COL_MOVIE_PLOT = "duration";
    public static final String COL_ID = "_id";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "greekMovie.db", null, 1);
    }

    //Η μέθοδος που θα καλεστεί την πρώτη φορά που θα καλέσεις κάποιο database object
    //Εδώ πρέπει να υπάρξει κώδικας που θα δημιουργήσει ένα νέο πίνακα
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + MOVIE_TABLE + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_MOVIE_NAME + " TEXT, " + COL_MOVIE_PLOT + " TEXT)";

        db.execSQL(createTableStatement);

    }

    //αυτή η μέθοδος καλείται όταν ανανεώνεται η βάση δεδομένων
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(Movie movie){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_MOVIE_NAME, movie.getName());
        cv.put(COL_MOVIE_PLOT, movie.getPlot());

        long insert = db.insert(MOVIE_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public List<Movie> getAllMovies() {

        List<Movie> returnList = new ArrayList<>();

        //κάνουμε select από τη βάση
        String query = "Select * FROM " + MOVIE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        //αν έχουμε true σημαίνει ότι επιστράφηκαν αποτελέσματα
        if (cursor.moveToFirst()){
            do {
                int movieID = cursor.getInt(0); //έβαλα μηδέν γιατί ξέρω ότι το id είναι στη θέση μηδέν
                String movieName = cursor.getString(1);
                String moviePlot = cursor.getString(2);

                Movie newMovie = new Movie(movieID,movieName,moviePlot);
                returnList.add(newMovie);
            }while(cursor.moveToNext()); //παίρνει ένα-ένα τα στοιχεία της βάσης
        }
        else{
            //επειδή δε θα έχει επιστραφεί κάτι από τη βάση, δε θα εισάγουμε τίποτα στη λίστα
        }
        cursor.close();
        db.close();

        return returnList;
    }
}
