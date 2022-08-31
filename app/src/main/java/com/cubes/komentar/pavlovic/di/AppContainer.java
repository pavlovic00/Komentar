package com.cubes.komentar.pavlovic.di;

import com.cubes.komentar.pavlovic.data.source.networking.NewsRetrofit;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;

public class AppContainer {

    NewsRetrofit api = new NewsRetrofit();

    public DataRepository dataRepository = new DataRepository(api.getRetrofitService());

}
