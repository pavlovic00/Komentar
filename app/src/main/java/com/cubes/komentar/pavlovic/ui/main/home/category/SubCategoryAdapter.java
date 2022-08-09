package com.cubes.komentar.pavlovic.ui.main.home.category;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.databinding.RvItemCategoryItemBinding;
import com.cubes.komentar.pavlovic.data.source.response.ResponseCategories;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder> {

    private final ArrayList<ResponseCategories.ResponseCategoriesData> list;


    public SubCategoryAdapter(ArrayList<ResponseCategories.ResponseCategoriesData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = RvItemCategoryItemBinding.inflate(inflater, parent, false);

        return new SubCategoryViewHolder(binding);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {

        ResponseCategories.ResponseCategoriesData categories = list.get(position);

        RvItemCategoryItemBinding bindingCategory = (RvItemCategoryItemBinding) holder.binding;

        bindingCategory.textViewCategory.setText(categories.name);
        bindingCategory.textViewCategory.setTextSize(15);
        bindingCategory.textViewCategory.setTextColor(Color.parseColor("#76FFFFFF"));
        bindingCategory.view.setVisibility(View.GONE);

        if (list.get(position).subcategories.size() == 0) {
            bindingCategory.submenuarrow.setVisibility(View.INVISIBLE);
        }

        bindingCategory.textViewCategory.setOnClickListener(view -> {
            Intent categoryIntent = new Intent(view.getContext(), CategoryActivity.class);
            categoryIntent.putExtra("id", list.get(position).id);
            categoryIntent.putExtra("category", list.get(position).name);
            view.getContext().startActivity(categoryIntent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SubCategoryViewHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public SubCategoryViewHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
