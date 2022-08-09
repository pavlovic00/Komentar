package com.cubes.komentar.pavlovic.ui.main.home.homepage;

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

import com.cubes.komentar.databinding.FragmentHomepageBinding;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.source.response.ResponseHomepage;

public class HomepageFragment extends Fragment {

    private FragmentHomepageBinding binding;
    public HomepageAdapter adapter;


    public HomepageFragment() {
        // Required empty public constructor
    }

    public static HomepageFragment newInstance() {
        HomepageFragment fragment = new HomepageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomepageBinding.inflate(inflater, container, false);

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

        binding.recyclerViewHomepage.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HomepageAdapter();
        binding.recyclerViewHomepage.setAdapter(adapter);

    }

    public void loadHomeData() {

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recyclerViewHomepage.setVisibility(View.GONE);

        DataRepository.getInstance().loadHomeData(new DataRepository.HomeResponseListener() {
            @Override
            public void onResponse(ResponseHomepage.ResponseHomepageData response) {

                adapter.setDataItems(response);

                binding.refresh.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.GONE);
                binding.recyclerViewHomepage.setVisibility(View.VISIBLE);
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
            loadHomeData();
            binding.progressBar.setVisibility(View.GONE);
        });
    }
}