package com.example.greekmovielist;

public class Contributor {

    private int id;
    private String name;
    private String role;

    public Contributor(int id, String name, String role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getId() {
        return id;
    }
}
