package com.cubes.komentar.pavlovic.ui.main.latest;

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
import com.cubes.komentar.databinding.FragmentLatestBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.di.AppContainer;
import com.cubes.komentar.pavlovic.di.MyApplication;
import com.cubes.komentar.pavlovic.ui.details.DetailsActivity;

import java.util.ArrayList;

public class LatestFragment extends Fragment {

    private FragmentLatestBinding binding;
    private LatestAdapter adapter;
    private int nextPage = 2;
    private DataRepository dataRepository;


    public static LatestFragment newInstance() {
        return new LatestFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppContainer appContainer = ((MyApplication) requireActivity().getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLatestBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.purple_light));
        binding.swipeRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadDataLatest();
            binding.progressBar.setVisibility(View.GONE);
        });

        setupRecyclerView();
        loadDataLatest();
        refresh();
    }

    public void setupRecyclerView() {

        binding.recyclerViewLatest.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LatestAdapter((newsId, newsIdList) -> {
            Intent intent = new Intent(getContext(), DetailsActivity.class);
            intent.putExtra("news_id", newsId);
            intent.putExtra("news_list_id", newsIdList);
            startActivity(intent);
        }, () -> dataRepository.loadLatestData(nextPage, new DataRepository.LatestResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {
                adapter.addNewsList(response);
                binding.recyclerViewLatest.setItemViewCacheSize(50);
                nextPage++;
            }

            @Override
            public void onFailure(Throwable t) {
                binding.recyclerViewLatest.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
            }
        }));

        binding.recyclerViewLatest.setAdapter(adapter);
    }

    public void loadDataLatest() {

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewLatest.setVisibility(View.GONE);

        dataRepository.loadLatestData(1, new DataRepository.LatestResponseListener() {
            @Override
            public void onResponse(ArrayList<News> response) {
                adapter.setData(response);
                binding.recyclerViewLatest.setItemViewCacheSize(50);
                nextPage = 2;

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewLatest.setVisibility(View.VISIBLE);
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
            loadDataLatest();
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}