package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.model.News;
import com.cubes.komentar.pavlovic.ui.details.DetailNewsAdapter;
import com.cubes.komentar.pavlovic.ui.details.NewsDetailActivity;
import com.squareup.picasso.Picasso;

public class RvItemRelatedNews implements RecyclerViewItemDetail {

    private News news;


    public RvItemRelatedNews(News news) {
        this.news = news;
    }

    @Override
    public int getType() {
        return 8;
    }

    @Override
    public void bind(DetailNewsAdapter.DetailNewsViewHolder holder) {

        RvItemSmallBinding binding = (RvItemSmallBinding) holder.binding;

        binding.textViewTitle.setText(news.title);
        binding.textViewDate.setText(news.created_at);
        binding.textViewCategory.setText(news.category.name);
        binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));

        Picasso.get().load(news.image).into(binding.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startDetailIntent = new Intent(view.getContext(), NewsDetailActivity.class);
                startDetailIntent.putExtra("id", news.id);
                view.getContext().startActivity(startDetailIntent);
            }
        });

    }
}
