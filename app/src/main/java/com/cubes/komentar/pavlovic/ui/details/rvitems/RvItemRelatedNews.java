package com.cubes.komentar.pavlovic.ui.details.rvitems;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemSmallBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.details.DetailsAdapter;
import com.cubes.komentar.pavlovic.ui.tools.MyMethodsClass;
import com.cubes.komentar.pavlovic.ui.tools.listener.DetailsListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvItemRelatedNews implements RecyclerViewItemDetail {

    private final News news;
    private final DetailsListener newsListener;
    private final int[] newsListId;


    public RvItemRelatedNews(News news, DetailsListener newsListener, ArrayList<News> newsList) {
        this.news = news;
        this.newsListener = newsListener;
        this.newsListId = MyMethodsClass.initNewsIdList(newsList);
    }

    @Override
    public int getType() {
        return R.layout.rv_item_small;
    }

    @Override
    public void bind(DetailsAdapter.ViewHolder holder) {

        RvItemSmallBinding binding = (RvItemSmallBinding) holder.binding;

        String date = "| " + news.createdAt.substring(11, 16);

        binding.textViewTitle.setText(news.title);
        binding.date.setText(date);
        binding.textViewCategory.setText(news.category.name);
        binding.textViewCategory.setTextColor(Color.parseColor(news.category.color));
        Picasso.get().load(news.image).into(binding.imageView);

        binding.more.setOnClickListener(view -> {
            FrameLayout viewGroup = holder.itemView.findViewById(R.id.frameLayout);
            LayoutInflater inflater = (LayoutInflater) holder.itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_window, viewGroup);
            PopupWindow popupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);

            ImageView comments = layout.findViewById(R.id.allComments);
            comments.setOnClickListener(view1 -> {
                newsListener.onCommentNewsClicked(news.id);
                popupWindow.dismiss();
            });

            ImageView share = layout.findViewById(R.id.share);
            share.setOnClickListener(view13 -> {
                newsListener.onShareNewsClicked(news.url);
                popupWindow.dismiss();
            });

            ImageView closeMenu = layout.findViewById(R.id.closeMenu);
            closeMenu.setOnClickListener(view14 -> popupWindow.dismiss());

            ImageView save = layout.findViewById(R.id.bookmark);
            if (newsListener.isSaved(news.id)) {
                save.setImageResource(R.drawable.ic_save);
            } else {
                save.setImageResource(R.drawable.ic_un_save);
            }

            save.setOnClickListener(view12 -> {
                newsListener.onSaveClicked(news.id, news.title);
                popupWindow.dismiss();
            });

            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.showAsDropDown(binding.view, 0, 0);
        });

        holder.itemView.setOnClickListener(view -> newsListener.onNewsClickedVP(news.id, newsListId));
    }
}
