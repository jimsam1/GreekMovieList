package com.example.greekmovielist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

//    Global μεταβλητές που τις χρησιμοποιώ συχνά και προτιμώ το autofill όταν τις γράφω για ευκολία
    public static final String MOVIE_TABLE = "MOVIE_TABLE";
    public static final String COL_MOVIE_NAME = "MOVIE_NAME";
    public static final String COL_MOVIE_PLOT = "MOVIE_PLOT";
    public static final String COL_ID = "MOVIE_ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "greekMovies.db", null, 1);
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
}
