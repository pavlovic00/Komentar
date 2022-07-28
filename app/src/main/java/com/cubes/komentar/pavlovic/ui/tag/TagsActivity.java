package com.cubes.komentar.pavlovic.ui.tag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.cubes.komentar.databinding.ActivityTagsBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.response.Response;
import com.cubes.komentar.pavlovic.ui.main.search.SearchAdapter;

import java.util.ArrayList;

public class TagsActivity extends AppCompatActivity {

    private ActivityTagsBinding binding;
    private int id;
    private String title;
    private ArrayList<News> news = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityTagsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id = getIntent().getExtras().getInt("id");
        title = getIntent().getExtras().getString("title");

        binding.textViewTag.setText(title);

        loadTagData();

        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void loadTagData(){

        DataRepository.getInstance().loadTagData(id, new DataRepository.TagResponseListener() {
            @Override
            public void onResponse(Response response) {
                binding.recyclerViewTags.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.recyclerViewTags.setAdapter(new SearchAdapter(getApplicationContext(),response.data.news));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
}