package com.cubes.komentar.pavlovic.ui.main.video;

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

import com.cubes.komentar.databinding.FragmentVideoBinding;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.source.response.ResponseNewsList;

public class VideoFragment extends Fragment {

    private FragmentVideoBinding binding;
    private VideoAdapter adapter;
    private int nextPage = 2;


    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentVideoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.swipeRefresh.setOnRefreshListener(() -> {
            setupRecyclerView();
            loadDataVideo();
            binding.progressBar.setVisibility(View.GONE);
        });

        setupRecyclerView();
        loadDataVideo();
        refresh();
    }

    public void setupRecyclerView() {

        binding.recyclerViewVideo.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new VideoAdapter(news -> {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_TEXT, news.url);
            i.setType("text/plain");
            Intent shareIntent = Intent.createChooser(i, null);
            startActivity(shareIntent);
        });

        binding.recyclerViewVideo.setAdapter(adapter);

        adapter.setLoadingNewsListener(() -> DataRepository.getInstance().loadVideoData(nextPage, new DataRepository.VideoResponseListener() {
            @Override
            public void onResponse(ResponseNewsList.ResponseData response) {
                adapter.addNewsList(response.news);

                nextPage++;
            }

            @Override
            public void onFailure(Throwable t) {
                binding.recyclerViewVideo.setVisibility(View.GONE);
                binding.refresh.setVisibility(View.VISIBLE);
            }
        }));

    }

    public void loadDataVideo() {

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewVideo.setVisibility(View.GONE);

        DataRepository.getInstance().loadVideoData(0, new DataRepository.VideoResponseListener() {
            @Override
            public void onResponse(ResponseNewsList.ResponseData response) {

                nextPage = 2;
                adapter.setData(response);

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewVideo.setVisibility(View.VISIBLE);
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
            loadDataVideo();
            binding.progressBar.setVisibility(View.GONE);
        });

    }
}