package com.cubes.komentar.pavlovic.ui.main.home.category;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.databinding.RvItemCategoryItemBinding;
import com.cubes.komentar.pavlovic.data.domain.Category;
import com.cubes.komentar.pavlovic.ui.tools.listener.SubcategoryListener;

import java.util.ArrayList;

public class SubcategoryAdapter extends RecyclerView.Adapter<SubcategoryAdapter.ViewHolder> {

    private final ArrayList<Category> subCategoryList;
    private final SubcategoryListener subCategoryListener;
    private int categoryId;


    public SubcategoryAdapter(Category category, SubcategoryListener subCategoryListener) {
        this.subCategoryList = category.subcategories;
        this.subCategoryListener = subCategoryListener;
        this.categoryId = category.id;
    }

    public SubcategoryAdapter(ArrayList<Category> subCategoryList, SubcategoryListener subCategoryListener) {
        this.subCategoryList = subCategoryList;
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

        Category subCategory = subCategoryList.get(position);

        RvItemCategoryItemBinding bindingCategory = (RvItemCategoryItemBinding) holder.binding;

        bindingCategory.textViewCategory.setText(subCategory.name);
        bindingCategory.textViewCategory.setTextSize(15);
        bindingCategory.textViewCategory.setTextColor(Color.parseColor("#76FFFFFF"));
        bindingCategory.view.setVisibility(View.GONE);

        if (subCategory.subcategories == null || subCategory.subcategories.size() == 0) {
            bindingCategory.submenuarrow.setVisibility(View.INVISIBLE);
        }

        bindingCategory.textViewCategory.setOnClickListener(view -> subCategoryListener.onSubCategoryClicked(categoryId, subCategory.id));
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public ViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
