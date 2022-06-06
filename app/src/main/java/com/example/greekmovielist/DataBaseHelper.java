package com.example.greekmovielist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

//    Global μεταβλητές που τις χρησιμοποιώ συχνά και προτιμώ το autofill όταν τις γράφω για ευκολία
    public Context myContext;
    public String DB_PATH;
    public SQLiteDatabase myDataBase;
    public static final String DB_NAME = "greekmovielist.db";
    public static final String MOVIE_TABLE = "movie";
    public static final String COL_MOVIE_NAME = "title";
    public static final String COL_MOVIE_PLOT = "duration";
    public static final String COL_ID = "_id";

    public DataBaseHelper(Context context) throws IOException {
        super(context,DB_NAME,null,1);
        this.myContext =context;
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        boolean dbexist = checkDatabase();
        if (dbexist) {
            opendatabase();
        } else {
            System.out.println("Database doesn't exist");
            createDatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDatabase() throws IOException {
        boolean dbexist = checkDatabase();
        if(!dbexist) {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch(IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDatabase() {

        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copyDatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0) {
            myoutput.write(buffer,0,length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }


    public List<Movie> getAllMovies() {
        List<Movie> returnList = new ArrayList<>();

        //κάνουμε select από τη βάση
        String query = "Select * FROM " + MOVIE_TABLE;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        //αν έχουμε true σημαίνει ότι επιστράφηκαν αποτελέσματα
        if (cursor.moveToFirst()){
            do {
                int movieID = cursor.getInt(0); //έβαλα μηδέν γιατί ξέρω ότι το id είναι στη θέση μηδέν
                String movieTitle = cursor.getString(1);
                String movieReleaseDate = cursor.getString(2);
                int movieDuration = cursor.getInt(3);
                String movieBasedOn = cursor.getString(4);
                String movieImageName = cursor.getString(5);
                String moviePlot = cursor.getString(6);

                ArrayList<Contributor> contributors = new ArrayList<>();
                query = "SELECT contributor__id, name, title\n" +
                        "FROM movie_has_contributor\n" +
                        "INNER JOIN contributor ON contributor__id = contributor._id\n" +
                        "INNER JOIN entity ON entity__id = entity._id\n" +
                        "INNER JOIN role ON role__id = role._id\n" +
                        "WHERE movie__id = " + movieID +
                        " ORDER BY title";
                Cursor contributorCursor = db.rawQuery(query, null);
                if(contributorCursor.moveToFirst()) {
                    do {
                       contributors.add(new Contributor(contributorCursor.getInt(0), contributorCursor.getString(1), contributorCursor.getString(2)));
                    } while(contributorCursor.moveToNext());
                }

                Movie newMovie = new Movie(movieID, movieTitle, movieReleaseDate, movieDuration, movieBasedOn, movieImageName, moviePlot, contributors);
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

    public Movie getMovieById(int movieid) {
        Movie movie = null;
        String query = "Select * FROM " + MOVIE_TABLE + " WHERE " + COL_ID + " = " + movieid;
        Cursor cursor = myDataBase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int movieID = cursor.getInt(0); //έβαλα μηδέν γιατί ξέρω ότι το id είναι στη θέση μηδέν
            String movieTitle = cursor.getString(1);
            String movieReleaseDate = cursor.getString(2);
            int movieDuration = cursor.getInt(3);
            String movieBasedOn = cursor.getString(4);
            String movieImageName = cursor.getString(5);
            String moviePlot = cursor.getString(6);

            ArrayList<Contributor> contributors = new ArrayList<>();
            query = "SELECT contributor__id, name, title\n" +
                    "FROM movie_has_contributor\n" +
                    "INNER JOIN contributor ON contributor__id = contributor._id\n" +
                    "INNER JOIN entity ON entity__id = entity._id\n" +
                    "INNER JOIN role ON role__id = role._id\n" +
                    "WHERE movie__id = " + movieID +
                    " ORDER BY title";
            Cursor contributorCursor = myDataBase.rawQuery(query, null);
            if(contributorCursor.moveToFirst()) {
                do {
                    contributors.add(new Contributor(contributorCursor.getInt(0), contributorCursor.getString(1), contributorCursor.getString(2)));
                } while(contributorCursor.moveToNext());
            }

            movie = new Movie(movieID, movieTitle, movieReleaseDate, movieDuration, movieBasedOn, movieImageName, moviePlot, contributors);
        }
        return movie;
    }
}
