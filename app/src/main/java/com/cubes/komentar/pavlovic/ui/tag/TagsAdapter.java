package com.cubes.komentar.pavlovic.ui.tag;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cubes.komentar.databinding.RvItemButtonTagBinding;
import com.cubes.komentar.pavlovic.data.model.Tags;

import java.util.ArrayList;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsHolder> {

    private ArrayList<Tags> list;
    private Context context;

    public TagsAdapter(ArrayList<Tags> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TagsAdapter.TagsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RvItemButtonTagBinding binding = RvItemButtonTagBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new TagsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TagsAdapter.TagsHolder holder, int position) {
        Tags tags = list.get(position);

        holder.binding.button.setText(tags.title);

        holder.binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tagsIntent = new Intent(view.getContext(), TagsActivity.class);
                tagsIntent.putExtra("id",tags.id);
                tagsIntent.putExtra("title",tags.title);
                view.getContext().startActivity(tagsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TagsHolder extends RecyclerView.ViewHolder {

        RvItemButtonTagBinding binding;

        public TagsHolder(@NonNull RvItemButtonTagBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
