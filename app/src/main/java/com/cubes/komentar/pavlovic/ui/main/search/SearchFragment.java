package com.cubes.komentar.pavlovic.ui.main.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cubes.komentar.databinding.FragmentSearchBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.response.Response;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private ArrayList<News> searchList = new ArrayList<News>();


    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String term = binding.editTextSearch.getText().toString();

                if (term.length() < 3) {
                    Toast.makeText(getContext(),
                            "Unesite neki karakter za pretragu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Log.d("TAG", "onClick:Search " + term);

                loadSearchData();
            }
        });

    }

    //Load Search Data.
    public void loadSearchData(){

        DataRepository.getInstance().loadSearchData(String.valueOf(binding.editTextSearch.getText()), new DataRepository.SearchResponseListener() {
            @Override
            public void onResponse(Response response) {
                searchList = response.data.news;

                binding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerViewSearch.setAdapter(new SearchAdapter(getContext(), searchList));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

}