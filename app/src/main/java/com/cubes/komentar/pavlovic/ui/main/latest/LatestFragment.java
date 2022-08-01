package com.cubes.komentar.pavlovic.ui.main.latest;

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

import com.cubes.komentar.databinding.FragmentLatestBinding;
import com.cubes.komentar.pavlovic.data.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.data.tools.NewsListener;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseNewsList;

import java.util.ArrayList;

public class LatestFragment extends Fragment {

    private FragmentLatestBinding binding;
    private ArrayList<News> newsList;
    private LatestAdapter adapter;
    private int page = 1;


    public LatestFragment() {
        // Required empty public constructor
    }

    public static LatestFragment newInstance() {
        LatestFragment fragment = new LatestFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLatestBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadDataLatest();
        refresh();
    }

    public void refresh() {

        binding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(300);
                binding.refresh.startAnimation(rotate);
                loadDataLatest();
            }
        });

    }

    public void loadDataLatest() {

        DataRepository.getInstance().loadLatestData(page, new DataRepository.LatestResponseListener() {
            @Override
            public void onResponse(ResponseNewsList.ResponseData response) {
                newsList = response.news;
                updateUI();

                binding.refresh.setVisibility(View.GONE);
                binding.recyclerViewLatest.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.refresh.setVisibility(View.VISIBLE);
            }
        });

    }

    public void updateUI() {

        binding.recyclerViewLatest.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LatestAdapter(getContext(), newsList);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsCLicked(News news) {
                DataRepository.getInstance().getNewsDetails(getContext(), news);
            }
        });

        loadMoreNews();

        binding.recyclerViewLatest.setAdapter(adapter);

    }

    public void loadMoreNews() {

        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews(int page) {
                DataRepository.getInstance().loadLatestData(page, new DataRepository.LatestResponseListener() {
                    @Override
                    public void onResponse(ResponseNewsList.ResponseData response) {
                        adapter.addNewsList(response.news);

                        if (response.news.size() < 20) {
                            adapter.setFinished(true);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        binding.recyclerViewLatest.setVisibility(View.GONE);
                        binding.refresh.setVisibility(View.VISIBLE);
                        adapter.setFinished(true);
                    }
                });
            }
        });

    }
}