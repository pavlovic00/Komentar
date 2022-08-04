package com.cubes.komentar.pavlovic.ui.main.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.cubes.komentar.databinding.FragmentHomeBinding;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseCategories;
import com.cubes.komentar.pavlovic.ui.main.home.homepage.ViewPagerAdapter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadCategoriesData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadCategoriesData();
        refresh();
    }

    public void loadCategoriesData() {

        DataRepository.getInstance().loadCategoriesData(new DataRepository.CategoriesResponseListener() {
            @Override
            public void onResponse(ResponseCategories response) {
                binding.viewPagerHome.setAdapter(new ViewPagerAdapter(getActivity().getSupportFragmentManager(), response.data));
                binding.tabLayout.setupWithViewPager(binding.viewPagerHome);

                binding.refresh.setVisibility(View.GONE);
                binding.viewPagerHome.setVisibility(View.VISIBLE);
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
                loadCategoriesData();
            }
        });
    }
}