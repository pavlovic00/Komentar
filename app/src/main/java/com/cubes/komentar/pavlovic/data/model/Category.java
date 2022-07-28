package com.cubes.komentar.pavlovic.data.model;

import java.io.Serializable;

public class Category implements Serializable {

    public String type;
    public int id;
    public String name;
    public String color;
    public String description;
    public Subcategory subcategory;
}
