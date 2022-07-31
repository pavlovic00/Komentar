package com.cubes.komentar.pavlovic.ui.main.home.category;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvItemCategoryItemBinding;
import com.cubes.komentar.pavlovic.data.response.responsecategories.ResponseCategoriesData;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private ArrayList<ResponseCategoriesData> list;

    public CategoryAdapter(Context context, ArrayList<ResponseCategoriesData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RvItemCategoryItemBinding binding = RvItemCategoryItemBinding.inflate(LayoutInflater.from(context),parent,false);

        return  new CategoryAdapter.CategoryViewHolder(binding);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        ResponseCategoriesData categories = list.get(position);

        holder.binding.textViewCategory.setText(categories.name);
        holder.binding.view.setBackgroundColor(Color.parseColor(categories.color));
        holder.binding.submenuarrow.setRotation(270);

        if (list.get(position).subcategories.size() == 0){
            holder.binding.submenuarrow.setVisibility(View.INVISIBLE);
        }

        holder.binding.submenuarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Jako bitno mora biti na pocetku.
                categories.open = !categories.open;

                if (categories.open) {
                   holder.binding.recyclerViewSubCategory.setLayoutManager(new LinearLayoutManager(context));
                   holder.binding.recyclerViewSubCategory.setAdapter(new SubCategoryAdapter(context, categories.subcategories));

                   holder.binding.submenuarrow.setRotation(90);

                } else {
                   holder.binding.recyclerViewSubCategory.setLayoutManager(new LinearLayoutManager(context));
                   holder.binding.recyclerViewSubCategory.setAdapter(new SubCategoryAdapter(context, new ArrayList<ResponseCategoriesData>()));

                   holder.binding.submenuarrow.setRotation(-90);
                }

           }
       });

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

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        private RvItemCategoryItemBinding binding;

        public CategoryViewHolder(RvItemCategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
