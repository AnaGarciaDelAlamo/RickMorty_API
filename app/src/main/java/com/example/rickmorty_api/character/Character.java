package com.example.rickmorty_api.character;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Character implements Serializable {
    private Location location;
    private Origin origin;

    private List<String> episode;

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private String status;
    @SerializedName("image")
    private String image;

    @SerializedName("species")
    private String species;

    @SerializedName("type")
    private String type;

    @SerializedName("gender")
    private String gender;
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getEpisode() {
        return episode;
    }

    public void setEpisode(List<String> episode) {
        this.episode = episode;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}