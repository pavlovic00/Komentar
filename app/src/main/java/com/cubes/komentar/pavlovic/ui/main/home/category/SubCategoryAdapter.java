package com.cubes.komentar.pavlovic.ui.main.home.category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvItemCategoryItemBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.data.response.ResponseCategories;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder> {

    private Context context;
    public ArrayList<News> newsList;
    private ArrayList<ResponseCategories.ResponseCategoriesData> list;

    public SubCategoryAdapter(Context context, ArrayList<ResponseCategories.ResponseCategoriesData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RvItemCategoryItemBinding binding = RvItemCategoryItemBinding.inflate(LayoutInflater.from(context), parent, false);

        return new SubCategoryAdapter.SubCategoryViewHolder(binding);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {

        ResponseCategories.ResponseCategoriesData categories = list.get(position);

        holder.binding.textViewCategory.setText(categories.name);
        holder.binding.textViewCategory.setTextSize(15);
        holder.binding.textViewCategory.setTextColor(Color.parseColor("#76FFFFFF"));
        holder.binding.view.setVisibility(View.GONE);

        if (list.get(position).subcategories.size() == 0) {
            holder.binding.submenuarrow.setVisibility(View.INVISIBLE);
        }

        holder.binding.textViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(context, CategoryActivity.class);
                categoryIntent.putExtra("id", list.get(position).id);
                categoryIntent.putExtra("category", list.get(position).name);
                context.startActivity(categoryIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SubCategoryViewHolder extends RecyclerView.ViewHolder {

        private RvItemCategoryItemBinding binding;

        public SubCategoryViewHolder(RvItemCategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
