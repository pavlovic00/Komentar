package com.cubes.komentar.pavlovic.ui.main.home.category;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager.widget.ViewPager;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemCategoryItemBinding;
import com.cubes.komentar.pavlovic.data.source.response.ResponseCategories;
import com.cubes.komentar.pavlovic.ui.main.menu.HomeActivity;
import com.cubes.komentar.pavlovic.ui.tools.SubCategoryListener;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final ArrayList<ResponseCategories.ResponseCategoriesData> list;
    private final SubCategoryListener subCategoryListener;


    public CategoryAdapter(ArrayList<ResponseCategories.ResponseCategoriesData> list, SubCategoryListener subCategoryListener) {
        this.list = list;
        this.subCategoryListener = subCategoryListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = RvItemCategoryItemBinding.inflate(inflater, parent, false);

        return new CategoryViewHolder(binding);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        ResponseCategories.ResponseCategoriesData categories = list.get(position);

        RvItemCategoryItemBinding bindingCategory = (RvItemCategoryItemBinding) holder.binding;

        bindingCategory.textViewCategory.setText(categories.name);
        bindingCategory.view.setBackgroundColor(Color.parseColor(categories.color));
        bindingCategory.submenuarrow.setRotation(270);

        if (list.get(position).subcategories.size() == 0) {
            bindingCategory.submenuarrow.setVisibility(View.INVISIBLE);
        }
        bindingCategory.submenuarrow.setOnClickListener(view -> {

            //Jako bitno mora biti na pocetku.
            categories.open = !categories.open;

            if (categories.open) {
                bindingCategory.recyclerViewSubCategory.setLayoutManager(new LinearLayoutManager(view.getContext()));
                bindingCategory.recyclerViewSubCategory.setAdapter(new SubCategoryAdapter(categories.subcategories, subCategoryListener));

                bindingCategory.submenuarrow.setRotation(90);
            } else {
                bindingCategory.recyclerViewSubCategory.setLayoutManager(new LinearLayoutManager(view.getContext()));
                bindingCategory.recyclerViewSubCategory.setAdapter(new SubCategoryAdapter(new ArrayList<>(), subCategoryListener));

                bindingCategory.submenuarrow.setRotation(-90);
            }

        });

        bindingCategory.textViewCategory.setOnClickListener(view -> {
            DrawerLayout drawer = ((HomeActivity) view.getContext()).findViewById(R.id.drawerLayout);
            ViewPager viewPager = ((HomeActivity) view.getContext()).findViewById(R.id.viewPagerHome);
            drawer.closeDrawer(((HomeActivity) view.getContext()).findViewById(R.id.navigationView));
            viewPager.setCurrentItem(position + 1);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public CategoryViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
