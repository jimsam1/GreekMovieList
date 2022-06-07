package com.example.greekmovielist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    public Context myContext;
    public String DB_PATH;
    public SQLiteDatabase myDataBase;
    public static final String DB_NAME = "greekmovielist.db";

    public DataBaseHelper(Context context) throws IOException {
        super(context,DB_NAME,null,1);
        this.myContext =context;
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        boolean dbexist = checkDatabase();
        //if database exists, open it - else create it.
        if (dbexist) {
            opendatabase();
        } else {
            System.out.println("Database doesn't exist");
            createDatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //do nothing
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //do nothing
    }

    //If package folder has no greekmovielist databse - create it from assets folder
    public void createDatabase() throws IOException {
        boolean dbexist = checkDatabase();
        if(!dbexist) {
            //creates empty db in phone
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch(IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    //Check if database already exists in package folder
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

    //Copy database from assets folder to package folder
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

    //Opens database
    public void opendatabase() throws SQLException {
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    //Closes database
    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    //Returns list of every movie in database as objects from Movie class
    public List<Movie> getAllMovies() {
        List<Movie> returnList = new ArrayList<>();

        //Select all data from movie table
        String query = "Select * FROM movie";

        Cursor cursor = myDataBase.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                returnList.add(getMovieFromCursor(cursor));
            } while(cursor.moveToNext());
        }
        cursor.close();
        return returnList;
    }

    //returns one movie identified by id from database as object of Movie class
    public Movie getMovieById(int movieid) {
        Movie movie = null;
        String query = "Select * FROM movie WHERE _id = " + movieid;
        Cursor cursor = myDataBase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            movie = getMovieFromCursor(cursor);
        }
        cursor.close();
        return movie;
    }

    //Processes query from search bar and returns all related movies in list with object from Movie class
    public ArrayList<Movie> getMovieListByQuery(String inputQuery) {
        Movie movie = null;
        ArrayList<Movie> movies = new ArrayList<>();
        //Query to select all movies that have "inputQuery" in movie title, movie description or in any movie contributors
        String query = "SELECT DISTINCT movie._id, movie.title, movie.releaseDate, movie.duration, movie.basedOn ,movie.imageName, movie.description\n" +
                "FROM movie_has_contributor\n" +
                "INNER JOIN contributor ON movie_has_contributor.contributor__id = contributor._id\n" +
                "INNER JOIN entity ON contributor.entity__id = entity._id\n" +
                "INNER JOIN movie ON movie_has_contributor.movie__id = movie._id\n" +
                "WHERE movie.title LIKE '%" + inputQuery + "%' OR movie.description LIKE '%" + inputQuery + "%' OR entity.name LIKE '%" + inputQuery + "%'\n" +
                " ORDER BY movie.title";
        Cursor cursor = myDataBase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                movies.add(getMovieFromCursor(cursor));
            } while(cursor.moveToNext());
        }
        return movies;
    }

    //returns a movies id from its title
    public int getMovieidByTitle(String title) {
        int id = 0;
        String query = "SELECT _id FROM movie WHERE title LIKE '%" + title + "%'";
        Cursor cursor = myDataBase.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        return id;
    }

    //creates and returns Movie object from info in cursor
    private Movie getMovieFromCursor(Cursor cursor) {
        //extracts info from cursor to new variables
        int movieID = cursor.getInt(0); //έβαλα μηδέν γιατί ξέρω ότι το id είναι στη θέση μηδέν
        String movieTitle = cursor.getString(1);
        String movieReleaseDate = cursor.getString(2);
        int movieDuration = cursor.getInt(3);
        String movieBasedOn = cursor.getString(4);
        String movieImageName = cursor.getString(5);
        String moviePlot = cursor.getString(6);

        //list with all contributors of current movie
        ArrayList<Contributor> contributors = new ArrayList<>();
        //query to return all contributors from current movie
        String query = "SELECT contributor__id, name, title\n" +
                "FROM movie_has_contributor\n" +
                "INNER JOIN contributor ON contributor__id = contributor._id\n" +
                "INNER JOIN entity ON entity__id = entity._id\n" +
                "INNER JOIN role ON role__id = role._id\n" +
                "WHERE movie__id = " + movieID +
                " ORDER BY title";
        Cursor contributorCursor = myDataBase.rawQuery(query, null);
        //fill contributors list
        if(contributorCursor.moveToFirst()) {
            do {
                contributors.add(new Contributor(contributorCursor.getInt(0), contributorCursor.getString(1), contributorCursor.getString(2)));
            } while(contributorCursor.moveToNext());
        }

        //create Movie with Movie constructor and return it
        return new Movie(movieID, movieTitle, movieReleaseDate, movieDuration, movieBasedOn, movieImageName, moviePlot, contributors);
    }
}
