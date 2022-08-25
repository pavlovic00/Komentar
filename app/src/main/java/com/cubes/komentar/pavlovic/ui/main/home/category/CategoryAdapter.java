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
import com.cubes.komentar.pavlovic.data.domain.Category;
import com.cubes.komentar.pavlovic.ui.main.menu.HomeActivity;
import com.cubes.komentar.pavlovic.ui.tools.listener.SubCategoryListener;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final ArrayList<Category> categoryList;
    private final SubCategoryListener subCategoryListener;


    public CategoryAdapter(ArrayList<Category> categoryList, SubCategoryListener subCategoryListener) {
        this.categoryList = categoryList;
        this.subCategoryListener = subCategoryListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = RvItemCategoryItemBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Category category = categoryList.get(position);

        RvItemCategoryItemBinding bindingCategory = (RvItemCategoryItemBinding) holder.binding;

        bindingCategory.textViewCategory.setText(category.name);
        bindingCategory.view.setBackgroundColor(Color.parseColor(category.color));
        bindingCategory.submenuarrow.setRotation(270);

        if (categoryList.get(position).subcategories.size() == 0) {
            bindingCategory.submenuarrow.setVisibility(View.INVISIBLE);
        }
        bindingCategory.submenuarrow.setOnClickListener(view -> {

            //Jako bitno mora biti na pocetku.
            category.open = !category.open;

            if (category.open) {
                bindingCategory.recyclerViewSubCategory.setLayoutManager(new LinearLayoutManager(view.getContext()));
                bindingCategory.recyclerViewSubCategory.setAdapter(new SubCategoryAdapter(category.subcategories, subCategoryListener));

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
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public ViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
