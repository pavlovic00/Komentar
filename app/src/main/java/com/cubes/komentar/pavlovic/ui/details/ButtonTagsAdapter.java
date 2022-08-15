package com.cubes.komentar.pavlovic.ui.details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.databinding.RvItemButtonTagBinding;
import com.cubes.komentar.pavlovic.data.model.Tags;
import com.cubes.komentar.pavlovic.ui.tools.NewsDetailListener;

import java.util.ArrayList;

public class ButtonTagsAdapter extends RecyclerView.Adapter<ButtonTagsAdapter.ButtonTagsHolder> {

    private final ArrayList<Tags> list;
    private final NewsDetailListener tagListener;


    public ButtonTagsAdapter(ArrayList<Tags> list, NewsDetailListener tagListener) {
        this.list = list;
        this.tagListener = tagListener;
    }

    @NonNull
    @Override
    public ButtonTagsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = RvItemButtonTagBinding.inflate(inflater, parent, false);

        return new ButtonTagsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonTagsHolder holder, int position) {

        Tags tags = list.get(position);

        RvItemButtonTagBinding bindingButton = (RvItemButtonTagBinding) holder.binding;

        bindingButton.button.setText(tags.title);

        bindingButton.button.setOnClickListener(view -> tagListener.onTagClicked(tags));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ButtonTagsHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public ButtonTagsHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
