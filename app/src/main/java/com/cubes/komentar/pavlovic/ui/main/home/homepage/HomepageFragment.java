package com.cubes.komentar.pavlovic.ui.main.home.homepage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.cubes.komentar.databinding.FragmentHomepageBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseHomepage;

import java.util.ArrayList;

public class HomepageFragment extends Fragment {

    private FragmentHomepageBinding binding;
    public ArrayList<News> newsList;
    public ResponseHomepage.ResponseHomepageData data;


    public HomepageFragment() {
        // Required empty public constructor
    }

    public static HomepageFragment newInstance(ArrayList<News> newsList) {
        HomepageFragment fragment = new HomepageFragment();
        fragment.newsList = newsList;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomepageBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadHomeData();
        refresh();
    }

    public void loadHomeData() {

        DataRepository.getInstance().loadHomeData(new DataRepository.HomeResponseListener() {
            @Override
            public void onResponse(ResponseHomepage response) {
                HomepageAdapter homepageAdapter = new HomepageAdapter(response.data);
                homepageAdapter.setListener(new HomepageAdapter.Listener() {
                    @Override
                    public void onNewsItemClick(News news) {

                    }
                });
                binding.recyclerViewHomepage.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerViewHomepage.setAdapter(homepageAdapter);

                binding.refresh.setVisibility(View.GONE);
                binding.recyclerViewHomepage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
            }
        });

    }

    public void refresh() {

        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(300);
                binding.refresh.startAnimation(rotate);
                loadHomeData();
            }
        });
    }
}