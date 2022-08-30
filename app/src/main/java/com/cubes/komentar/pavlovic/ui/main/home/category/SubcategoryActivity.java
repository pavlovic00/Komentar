package com.cubes.komentar.pavlovic.ui.main.home.category;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentar.databinding.ActivitySubcategoryNewsBinding;
import com.cubes.komentar.pavlovic.data.domain.Category;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.di.AppContainer;
import com.cubes.komentar.pavlovic.di.MyApplication;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class SubcategoryActivity extends AppCompatActivity {

    private ActivitySubcategoryNewsBinding binding;
    private Category mCategory = new Category();
    private int mCategoryId;
    private int mSubcategoryId;
    private DataRepository dataRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubcategoryNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mCategoryId = getIntent().getIntExtra("category_id", -1);
        mSubcategoryId = getIntent().getIntExtra("subcategory_id", -1);

        getAllCategories(mCategoryId, mSubcategoryId);

        binding.imageViewBack.setOnClickListener(view -> finish());
        binding.imageViewRefresh.setOnClickListener(view -> getAllCategories(mCategoryId, mSubcategoryId));
        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;
    }

    private void getAllCategories(int mCategoryId, int mSubcategoryId) {

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.imageViewRefresh.setVisibility(View.GONE);

        dataRepository.loadCategoriesData(new DataRepository.CategoriesResponseListener() {
            @Override
            public void onResponse(ArrayList<Category> categories) {

                binding.progressBar.setVisibility(View.GONE);
                binding.linearLayoutCategoryPager.setVisibility(View.VISIBLE);

                for (int i = 0; i < categories.size(); i++) {
                    if (categories.get(i).id == mCategoryId) {
                        mCategory = categories.get(i);
                        break;
                    }
                }

                int currentSubcategoryPosition = -1;


                for (int i = 0; i < mCategory.subcategories.size(); i++) {
                    if (mSubcategoryId == mCategory.subcategories.get(i).id) {
                        currentSubcategoryPosition = i;
                        break;
                    }
                }

                int[] subcategoryIdList = new int[mCategory.subcategories.size()];


                for (int i = 0; i < mCategory.subcategories.size(); i++) {
                    subcategoryIdList[i] = mCategory.subcategories.get(i).id;
                }

                SubcategoryPagerAdapter adapter = new SubcategoryPagerAdapter(SubcategoryActivity.this, subcategoryIdList, mCategory.name);
                binding.viewPager.setAdapter(adapter);

                new TabLayoutMediator(
                        binding.tabLayout,
                        binding.viewPager,
                        (tab, position) -> tab.setText(mCategory.subcategories.get(position).name)
                ).attach();

                binding.viewPager.setCurrentItem(currentSubcategoryPosition);
            }

            @Override
            public void onFailure(Throwable t) {
                binding.imageViewRefresh.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                binding.linearLayoutCategoryPager.setVisibility(View.GONE);
            }
        });
    }
}
