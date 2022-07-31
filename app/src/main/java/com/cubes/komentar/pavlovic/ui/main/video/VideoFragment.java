package com.cubes.komentar.pavlovic.ui.main.video;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cubes.komentar.databinding.FragmentVideoBinding;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.response.Response;

public class VideoFragment extends Fragment {

    private FragmentVideoBinding binding;


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

    public void loadDataVideo(){

        DataRepository.getInstance().loadVideoData(new DataRepository.VideoResponseListener() {
            @Override
            public void onResponse(Response response) {
                //ovo je jako bitno!
                binding.recyclerViewVideo.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerViewVideo.setAdapter(new VideoAdapter(getContext(), response.data.news));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= FragmentVideoBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadDataVideo();
    }
}