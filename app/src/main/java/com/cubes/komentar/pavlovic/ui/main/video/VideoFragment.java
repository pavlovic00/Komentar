package com.cubes.komentar.pavlovic.ui.main.video;

import android.content.Intent;
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

import com.cubes.komentar.databinding.FragmentVideoBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseNewsList;
import com.cubes.komentar.pavlovic.data.tools.LoadingNewsListener;
import com.cubes.komentar.pavlovic.data.tools.NewsListener;

import java.util.ArrayList;

public class VideoFragment extends Fragment {

    private FragmentVideoBinding binding;
    private VideoAdapter adapter;
    private int page = 1;


    public VideoFragment() {
        // Required empty public constructor
    }


    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentVideoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        loadDataVideo();
        refresh();
    }

    public void setupRecyclerView(){

        binding.recyclerViewVideo.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new VideoAdapter();
        binding.recyclerViewVideo.setAdapter(adapter);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsCLicked(News news) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT, news.url);
                i.setType("text/plain");
                Intent shareIntent = Intent.createChooser(i, null);
                getContext().startActivity(shareIntent);
            }
        });

        adapter.setLoadingNewsListener(new LoadingNewsListener() {
            @Override
            public void loadMoreNews(int page) {
                DataRepository.getInstance().loadVideoData(page, new DataRepository.VideoResponseListener() {
                    @Override
                    public void onResponse(ResponseNewsList.ResponseData response) {
                        adapter.addNewsList(response.news);

                        if (response.news.size() < 20) {
                            adapter.setFinished(true);
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        binding.recyclerViewVideo.setVisibility(View.GONE);
                        binding.refresh.setVisibility(View.VISIBLE);
                        adapter.setFinished(true);
                    }
                });
            }
        });

    }

    public void loadDataVideo() {

        DataRepository.getInstance().loadVideoData(page, new DataRepository.VideoResponseListener() {
            @Override
            public void onResponse(ResponseNewsList.ResponseData response) {

                adapter.setData(response);

                binding.refresh.setVisibility(View.GONE);
                binding.recyclerViewVideo.setVisibility(View.VISIBLE);
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
                loadDataVideo();
            }
        });

    }
}