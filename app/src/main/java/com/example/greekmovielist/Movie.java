package com.example.greekmovielist;

public class Movie {

    private int id;
    private String title;
    private String releaseDate;
    private int duration;
    private String basedOn;
    private String imageName;
    private String description;

//    constructors
    public Movie(int id, String title, String releaseDate, int duration, String basedOn, String imageName, String description) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.basedOn = basedOn;
        this.imageName = imageName;
        this.description = description;
    }

//  toString ώστε να εκτυπώνουμε τα δεδομένα του αντικειμένου της κλάσης
    @Override
    public String toString() {
        return "MovieModel{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    //    getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setName(String title) {
        this.title = title;
    }

    public String getPlot() {
        return description;
    }

    public void setPlot(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
