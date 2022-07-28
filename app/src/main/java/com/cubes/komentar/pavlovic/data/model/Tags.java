package com.cubes.komentar.pavlovic.data.model;

import java.io.Serializable;

public class Tags implements Serializable {

    public int id;
    public String title;

    public Tags(int id, String title) {
        this.id = id;
        this.title = title;
    }
}
