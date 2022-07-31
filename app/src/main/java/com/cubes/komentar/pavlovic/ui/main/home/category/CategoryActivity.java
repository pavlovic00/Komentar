package com.cubes.komentar.pavlovic.ui.main.home.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.cubes.komentar.databinding.ActivityCategoryBinding;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.response.Response;
import com.cubes.komentar.pavlovic.ui.main.latest.LatestAdapter;

public class CategoryActivity extends AppCompatActivity {

    private ActivityCategoryBinding binding;
    private String category;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        category = getIntent().getExtras().getString("category");

        id = getIntent().getExtras().getInt("id");

        DataRepository.getInstance().loadCategoryData(id, new DataRepository.CategoryResponseListener() {
            @Override
            public void onResponse(Response response) {

                binding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.recyclerViewCategory.setAdapter(new LatestAdapter(getApplicationContext(), response.data.news));

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });


        binding.textViewTag.setText(category);


        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}