package com.cubes.komentar.pavlovic.data.response;

import com.cubes.komentar.pavlovic.data.model.News;

import java.io.Serializable;

public class ResponseForPaging implements Serializable {

    private int status;
    private String message;
    public News data;

}
