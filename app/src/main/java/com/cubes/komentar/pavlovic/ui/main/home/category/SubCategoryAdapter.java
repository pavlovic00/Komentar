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
import com.cubes.komentar.pavlovic.data.response.responsecategories.ResponseCategoriesData;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder> {

    private Context context;
    private ArrayList<ResponseCategoriesData> list;

    public SubCategoryAdapter(Context context, ArrayList<ResponseCategoriesData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RvItemCategoryItemBinding binding = RvItemCategoryItemBinding.inflate(LayoutInflater.from(context),parent,false);

        return  new SubCategoryAdapter.SubCategoryViewHolder(binding);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {

        ResponseCategoriesData categories = list.get(position);

        holder.binding.textViewCategory.setText(categories.name);
        holder.binding.textViewCategory.setTextSize(15);
        holder.binding.textViewCategory.setTextColor(Color.parseColor("#76FFFFFF"));
        holder.binding.view.setVisibility(View.GONE);

        if (list.get(position).subcategories.size() == 0){
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

    public class SubCategoryViewHolder extends RecyclerView.ViewHolder{

        private RvItemCategoryItemBinding binding;

        public SubCategoryViewHolder(RvItemCategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
