package com.example.rickmorty_api.character;


import java.util.List;

public class Character {
    private int id;
    private String name;
    private String status;
    private String species;
    private String image;
    private String type;
    private String gender;
    private Location origin;
    private Location location;


    public Character(int id, String name, String status, String species, String image, String type, String gender, Location origin, Location location) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.image = image;
        this.type = type;
        this.gender = gender;
        this.origin = origin;
        this.location = location;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


}
