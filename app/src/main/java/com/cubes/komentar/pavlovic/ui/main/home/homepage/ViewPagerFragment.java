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
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseNewsList;
import com.cubes.komentar.pavlovic.data.response.ResponseCategories;
import com.cubes.komentar.pavlovic.data.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.data.tools.NewsListener;
import com.cubes.komentar.pavlovic.ui.main.latest.LatestAdapter;

import java.util.ArrayList;


public class ViewPagerFragment extends Fragment {

    private FragmentViewPagerBinding binding;
    public ArrayList<News> newsList;
    private ResponseCategories.ResponseCategoriesData category;
    private LatestAdapter adapter;
    private int page = 1;


    public ViewPagerFragment() {
        // Required empty public constructor
    }

    public static ViewPagerFragment newInstance(ResponseCategories.ResponseCategoriesData category) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.category = category;
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

        loadHomeData();
    }

    public void loadHomeData() {

        DataRepository.getInstance().loadCategoriesNewsData(category.id, page, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ResponseNewsList.ResponseData response) {
                if (response != null) {
                    newsList = response.news;
                }
                updateUI();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    public void updateUI() {

        binding.recyclerViewPager.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LatestAdapter(getContext(), newsList);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsCLicked(News news) {
                DataRepository.getInstance().getNewsDetails(getContext(), news);
            }
        });

        loadMoreNews();

        binding.recyclerViewPager.setAdapter(adapter);

    }

    public void loadMoreNews() {

        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews(int page) {
                DataRepository.getInstance().loadCategoriesNewsData(category.id, page, new DataRepository.NewsResponseListener() {
                    @Override
                    public void onResponse(ResponseNewsList.ResponseData response) {
                        adapter.addNewsList(response.news);

                        if (response.news.size() < 20) {
                            adapter.setFinished(true);
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        adapter.setFinished(true);
                    }
                });
            }
        });

    }

}