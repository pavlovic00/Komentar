package com.cubes.komentar.pavlovic.ui.main.home.homepage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cubes.komentar.databinding.FragmentViewPagerBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.networking.RetrofitService;
import com.cubes.komentar.pavlovic.data.response.response.Response;
import com.cubes.komentar.pavlovic.ui.main.latest.LatestAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewPagerFragment extends Fragment {

    private FragmentViewPagerBinding binding;
    public ArrayList<News> newsList;
    public int id;

    public ViewPagerFragment() {
        // Required empty public constructor
    }

    public static ViewPagerFragment newInstance(int id) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.id = id;
        fragment.newsList = new ArrayList<News>();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://komentar.rs/wp-json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        service.getAllNews(id).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                newsList = response.body().data.news;

                binding.recyclerViewPager.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerViewPager.setAdapter(new LatestAdapter(getContext(), newsList));
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

        binding.recyclerViewPager.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewPager.setAdapter(new LatestAdapter(getContext(), newsList));

    }
}