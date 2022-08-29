package com.cubes.komentar.pavlovic.data.source.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsRetrofit {

    public static final String BASE_URL = "https://komentar.rs/wp-json/";
    private RetrofitService retrofitService;

    public NewsRetrofit() {
        buildRetrofit();
    }

    public RetrofitService getRetrofitService() {
        return this.retrofitService;
    }

    private void buildRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NewsRetrofit.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(RetrofitService.class);
    }

}
