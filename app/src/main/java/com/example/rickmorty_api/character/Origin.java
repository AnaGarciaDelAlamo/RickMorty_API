package com.example.rickmorty_api.character;

import java.io.Serializable;

public class Origin implements Serializable {
    private String name;

    public Origin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
