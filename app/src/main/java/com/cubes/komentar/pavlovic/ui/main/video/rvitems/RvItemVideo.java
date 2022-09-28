package com.cubes.komentar.pavlovic.ui.main.video.rvitems;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemVideoBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.main.video.VideoAdapter;
import com.cubes.komentar.pavlovic.ui.tools.MyMethodsClass;
import com.cubes.komentar.pavlovic.ui.tools.listener.NewsListener;
import com.cubes.komentar.pavlovic.ui.tools.listener.VideoListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RvItemVideo implements RecyclerViewItemVideo {

    private final News video;
    private final NewsListener newsListener;
    private final int[] newsListId;
    private final VideoListener videoListener;


    public RvItemVideo(News video, NewsListener newsListener, ArrayList<News> newsList, VideoListener videoListener) {
        this.video = video;
        this.newsListener = newsListener;
        this.videoListener = videoListener;
        this.newsListId = MyMethodsClass.initNewsIdList(newsList);
    }

    @Override
    public int getType() {
        return R.layout.rv_item_video;
    }

    @Override
    public void bind(VideoAdapter.ViewHolder holder) {

        RvItemVideoBinding bindingVideo = (RvItemVideoBinding) holder.binding;

        bindingVideo.textViewTitle.setText(video.title);
        bindingVideo.date.setText(video.createdAt);
        bindingVideo.textViewCategory.setText(video.category.name);
        bindingVideo.textViewCategory.setTextColor(Color.parseColor(video.category.color));
        Picasso.get().load(video.image).into(bindingVideo.imageView);

        bindingVideo.more.setOnClickListener(view -> {
            FrameLayout viewGroup = holder.itemView.findViewById(R.id.frameLayout);
            LayoutInflater inflater = (LayoutInflater) holder.itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_window, viewGroup);
            PopupWindow popupWindow = new PopupWindow(layout, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, true);

            ImageView comments = layout.findViewById(R.id.allComments);
            comments.setOnClickListener(view1 -> {
                newsListener.onCommentNewsClicked(video.id);
                popupWindow.dismiss();
            });

            ImageView share = layout.findViewById(R.id.share);
            share.setOnClickListener(view13 -> {
                newsListener.onShareNewsClicked(video.url);
                popupWindow.dismiss();
            });

            ImageView closeMenu = layout.findViewById(R.id.closeMenu);
            closeMenu.setOnClickListener(view14 -> popupWindow.dismiss());

            ImageView save = layout.findViewById(R.id.bookmark);
            if (newsListener.isSaved(video.id)) {
                save.setImageResource(R.drawable.ic_save);
            } else {
                save.setImageResource(R.drawable.ic_un_save);
            }

            save.setOnClickListener(view12 -> {
                newsListener.onSaveClicked(video.id, video.title);
                popupWindow.dismiss();
            });

            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.showAsDropDown(bindingVideo.view, 0, 0);
        });

        bindingVideo.imageView.setOnClickListener(view -> newsListener.onNewsClickedVP(video.id, newsListId));
        bindingVideo.imageViewPlay.setOnClickListener(view -> videoListener.onVideoClicked(video.url, video.title));
    }
}
