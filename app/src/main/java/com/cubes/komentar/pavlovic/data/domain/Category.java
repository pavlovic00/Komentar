package com.cubes.komentar.pavlovic.data.domain;

import java.util.ArrayList;

public class Category {

    public String type;
    public int id;
    public String name;
    public String color;
    public ArrayList<Category> subcategories;
    public boolean open;
}
