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

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.FragmentViewPagerCategoryBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.di.AppContainer;
import com.cubes.komentar.pavlovic.di.MyApplication;
import com.cubes.komentar.pavlovic.ui.details.DetailsActivity;
import com.cubes.komentar.pavlovic.ui.main.latest.LatestAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class HomePagerFragment extends Fragment {

    private FragmentViewPagerCategoryBinding binding;
    private static final String CATEGORY_ID = "categoryId";
    private static final String CATEGORY_NAME = "categoryName";
    private int categoryId;
    private String categoryName;
    private LatestAdapter adapter;
    private int nextPage = 2;
    private FirebaseAnalytics mFirebaseAnalytics;
    private DataRepository dataRepository;


    public static HomePagerFragment newInstance(int categoryId, String categoryName) {
        HomePagerFragment fragment = new HomePagerFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, categoryId);
        args.putString(CATEGORY_NAME, categoryName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getInt(CATEGORY_ID);
            categoryName = getArguments().getString(CATEGORY_NAME);
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireActivity());

        AppContainer appContainer = ((MyApplication) requireActivity().getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewPagerCategoryBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.purple_light));
        binding.swipeRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadCategoriesHomeData();
            binding.progressBar.setVisibility(View.GONE);
        });

        setupRecyclerView();
        loadCategoriesHomeData();
        refresh();
    }

    public void setupRecyclerView() {
        binding.recyclerViewPager2.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LatestAdapter((newsId, newsIdList) -> {
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            intent.putExtra("news_id", newsId);
            intent.putExtra("news_list_id", newsIdList);
            startActivity(intent);
        }, () -> dataRepository.loadCategoriesNewsData(categoryId, nextPage, new DataRepository.CategoriesNewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {

                if (response == null || response.size() == 0) {
                    adapter.removeItem();
                } else {
                    adapter.addNewsList(response);
                    nextPage++;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                binding.recyclerViewPager2.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
            }
        }));

        binding.recyclerViewPager2.setAdapter(adapter);
    }

    public void loadCategoriesHomeData() {

        Bundle bundle = new Bundle();
        bundle.putString("category", categoryName);
        mFirebaseAnalytics.logEvent("android_komentar", bundle);

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewPager2.setVisibility(View.GONE);

        dataRepository.loadCategoriesNewsData(categoryId, 0, new DataRepository.CategoriesNewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {
                adapter.setData(response);
                binding.recyclerViewPager2.setItemViewCacheSize(50);
                nextPage = 2;

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewPager2.setVisibility(View.VISIBLE);
                binding.swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
                binding.swipeRefresh.setRefreshing(false);
            }
        });

    }

    public void refresh() {

        binding.refresh.setOnClickListener(view -> {

            RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(300);
            binding.refresh.startAnimation(rotate);
            setupRecyclerView();
            loadCategoriesHomeData();
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}