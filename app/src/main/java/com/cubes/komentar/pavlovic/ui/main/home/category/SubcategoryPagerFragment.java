package com.cubes.komentar.pavlovic.ui.main.home.category;

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

import com.cubes.komentar.databinding.FragmentViewPagerSubcategoryBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;
import com.cubes.komentar.pavlovic.ui.main.latest.LatestAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class SubcategoryPagerFragment extends Fragment {

    private static final String ARG_CATEGORY_ID = "category_id";
    private static final String SUBCATEGORY_NAME = "subcategoryName";
    private FragmentViewPagerSubcategoryBinding binding;
    private int mCategoryId;
    private LatestAdapter adapter;
    private String subcategoryName;
    private int nextPage = 2;
    private FirebaseAnalytics mFirebaseAnalytics;


    public SubcategoryPagerFragment() {
    }

    public static SubcategoryPagerFragment newInstance(String subcategoryName, int categoryId) {
        SubcategoryPagerFragment fragment = new SubcategoryPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, categoryId);
        args.putString(SUBCATEGORY_NAME, subcategoryName);
        fragment.setArguments(args);

        fragment.mCategoryId = categoryId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategoryId = getArguments().getInt(ARG_CATEGORY_ID);
            subcategoryName = getArguments().getString(SUBCATEGORY_NAME);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewPagerSubcategoryBinding.inflate(inflater, container, false);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireActivity());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        adapter = new LatestAdapter(news -> {
            Intent i = new Intent(getContext(), NewsDetailActivity.class);
            i.putExtra("id", news.id);
            i.putExtra("title", news.title);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }, () -> DataRepository.getInstance().loadCategoriesNewsData(mCategoryId, nextPage, new DataRepository.CategoriesNewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {
                adapter.addNewsList(response);

                nextPage++;
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
        bundle.putString("subcategory", subcategoryName);
        mFirebaseAnalytics.logEvent("selected_subcategory", bundle);

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewPager2.setVisibility(View.GONE);

        DataRepository.getInstance().loadCategoriesNewsData(mCategoryId, 0, new DataRepository.CategoriesNewsResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {

                adapter.setData(response);

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