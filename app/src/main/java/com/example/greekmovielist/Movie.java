package com.example.greekmovielist;

import java.util.ArrayList;

public class Movie {

    private int id;
    private String title;
    private String releaseDate;
    private int duration;
    private String basedOn;
    private String imageName;
    private String description;
    private ArrayList<Contributor> contributors;

    //constructors
    public Movie(int id, String title, String releaseDate, int duration, String basedOn, String imageName, String description, ArrayList<Contributor> contributors) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.basedOn = basedOn;
        this.imageName = imageName;
        this.description = description;
        this.contributors = contributors;
    }


    //getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public ArrayList<Contributor> getContributors() {
        return contributors;
    }

    public void setContributors(ArrayList<Contributor> contributors) {
        this.contributors = contributors;
    }

    public String getDurationString() {
        return String.valueOf(duration);
    }

    public String getBasedOn() {
        return basedOn;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
