package com.example.greekmovielist;

public class Movie {

    private int id;
    private String name;
    private String plot;

//    constructors
    public Movie(int id, String name, String plot) {
        this.id = id;
        this.name = name;
        this.plot = plot;
    }

    public Movie() {
    }

//  toString ώστε να εκτυπώνουμε τα δεδομένα του αντικειμένου της κλάσης
    @Override
    public String toString() {
        return "MovieModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", plot='" + plot + '\'' +
                '}';
    }

    //    getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}
