package com.cubes.komentar.pavlovic.ui.main.home.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentar.databinding.FragmentViewPagerBinding;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.source.response.ResponseNewsList;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;
import com.cubes.komentar.pavlovic.ui.main.latest.LatestAdapter;

public class ViewPagerFragment extends Fragment {

    private FragmentViewPagerBinding binding;
    private static final String CATEGORY_ID = "categoryId";
    private int categoryId;
    private LatestAdapter adapter;
    private int nextPage = 1;


    public ViewPagerFragment() {
        // Required empty public constructor
    }

    public static ViewPagerFragment newInstance(int categoryId) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt(CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        loadHomeData();
        refresh();
    }

    public void setupRecyclerView() {
        binding.recyclerViewPager.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LatestAdapter();
        binding.recyclerViewPager.setAdapter(adapter);

        adapter.setNewsListener(news -> {
            Intent i = new Intent(getContext(), NewsDetailActivity.class);
            i.putExtra("id", news.id);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(i);
        });

        adapter.setLoadingNewsListener(() -> DataRepository.getInstance().loadCategoriesNewsData(categoryId, nextPage, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ResponseNewsList.ResponseData response) {
                adapter.addNewsList(response.news);

                nextPage++;
            }

            @Override
            public void onFailure(Throwable t) {
                binding.recyclerViewPager.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
            }
        }));

    }

    public void loadHomeData() {

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewPager.setVisibility(View.GONE);

        DataRepository.getInstance().loadCategoriesNewsData(categoryId, 1, new DataRepository.NewsResponseListener() {
            @Override
            public void onResponse(ResponseNewsList.ResponseData response) {

                adapter.setData(response);

                nextPage++;

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewPager.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
            }
        });

    }

    public void refresh() {

        binding.refresh.setOnClickListener(view -> {

            RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            binding.refresh.startAnimation(rotate);
            setupRecyclerView();
            loadHomeData();
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}