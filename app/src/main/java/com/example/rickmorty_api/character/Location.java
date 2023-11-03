package com.example.rickmorty_api.character;

import android.os.Parcel;

import java.io.Serializable;

public class Location implements Serializable {
    private String name;
    private String url;

    public Location(String name, String url) {
        this.name = name;
        this.url = url;
    }

    protected Location(Parcel in) {
        name = in.readString();
        url = in.readString();
    }
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

}
