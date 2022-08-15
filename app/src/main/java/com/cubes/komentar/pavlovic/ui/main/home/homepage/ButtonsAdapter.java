package com.cubes.komentar.pavlovic.ui.main.home.homepage;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentar.databinding.RvItemTextForNewsHomepageBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.tools.NewsListener;

import java.util.ArrayList;

public class ButtonsAdapter extends RecyclerView.Adapter<ButtonsAdapter.ButtonsHolder> {

    private final ArrayList<News> list;
    private final NewsListener buttonsListener;


    public ButtonsAdapter(ArrayList<News> list, NewsListener buttonsListener) {
        this.list = list;
        this.buttonsListener = buttonsListener;
    }

    @NonNull
    @Override
    public ButtonsAdapter.ButtonsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = RvItemTextForNewsHomepageBinding.inflate(inflater, parent, false);

        return new ButtonsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonsAdapter.ButtonsHolder holder, int position) {
        News news = list.get(position);

        RvItemTextForNewsHomepageBinding binding = (RvItemTextForNewsHomepageBinding) holder.binding;

        binding.textViewTitle.setText(news.title);
        binding.date.setText(news.created_at);

        holder.itemView.setOnClickListener(view -> buttonsListener.onNewsClicked(news));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ButtonsHolder extends RecyclerView.ViewHolder {

        public ViewBinding binding;

        public ButtonsHolder(@NonNull ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
