package com.cubes.komentar.pavlovic.ui.details;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.databinding.RvItemBigBinding;
import com.cubes.komentar.databinding.RvItemButtonTagBinding;
import com.cubes.komentar.pavlovic.data.model.Tags;
import com.cubes.komentar.pavlovic.ui.tag.TagsActivity;

import java.util.ArrayList;

public class ButtonTagsAdapter extends RecyclerView.Adapter<ButtonTagsAdapter.ButtonTagsHolder> {

    private ArrayList<Tags> list;


    public ButtonTagsAdapter(ArrayList<Tags> list) {
        this.list = list;
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

        bindingButton.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tagsIntent = new Intent(view.getContext(), TagsActivity.class);
                tagsIntent.putExtra("id", tags.id);
                tagsIntent.putExtra("title", tags.title);
                tagsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(tagsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ButtonTagsHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public ButtonTagsHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
