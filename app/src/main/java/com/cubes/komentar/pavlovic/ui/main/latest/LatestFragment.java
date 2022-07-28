package com.cubes.komentar.pavlovic.ui.main.latest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cubes.komentar.databinding.FragmentLatestBinding;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.response.Response;

public class LatestFragment extends Fragment {

    private FragmentLatestBinding binding;


    public LatestFragment() {
        // Required empty public constructor
    }

    public static LatestFragment newInstance() {
        LatestFragment fragment = new LatestFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void loadDataLatest(){

        DataRepository.getInstance().loadLatestData(new DataRepository.LatestResponseListener() {
            @Override
            public void onResponse(Response response) {
                //Ovo je jako bitno.
                binding.recyclerViewLatest.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerViewLatest.setAdapter(new LatestAdapter(getContext(), response.data.news));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= FragmentLatestBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadDataLatest();
    }
}