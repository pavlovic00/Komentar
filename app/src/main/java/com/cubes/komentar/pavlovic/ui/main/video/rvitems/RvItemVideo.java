package com.cubes.komentar.pavlovic.ui.main.video.rvitems;

import android.graphics.Color;

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

        bindingVideo.imageView.setOnClickListener(view -> newsListener.onNewsClickedVP(video.id, newsListId));
        bindingVideo.imageViewPlay.setOnClickListener(view -> videoListener.onVideoClicked(video.url, video.title));
    }
}
