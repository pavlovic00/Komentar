package com.cubes.komentar.pavlovic.ui.main.home.homepage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cubes.komentar.databinding.FragmentHomepageBinding;
import com.cubes.komentar.pavlovic.data.DataContainer;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.responsehomepage.ResponseHomepage;
import com.cubes.komentar.pavlovic.data.response.responsehomepage.ResponseHomepageData;

import java.util.ArrayList;

public class HomepageFragment extends Fragment {

    private FragmentHomepageBinding binding;
    public ArrayList<News> newsList;
    public ResponseHomepageData data;

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

    }

    public void loadHomeData(){

        DataRepository.getInstance().loadHomeData(new DataRepository.HomeResponseListener() {
            @Override
            public void onResponse(ResponseHomepage response) {
                HomepageAdapter homepageAdapter = new HomepageAdapter(response.data, getContext());
                homepageAdapter.setListener(new HomepageAdapter.Listener() {
                    @Override
                    public void onNewsItemClick(News news) {

                    }
                });
                binding.recyclerViewHomepage.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerViewHomepage.setAdapter(homepageAdapter);

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

}