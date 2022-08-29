package com.cubes.komentar.pavlovic.ui.main.video.rvitems;

import android.graphics.Color;

import com.cubes.komentar.R;
import com.cubes.komentar.databinding.RvItemVideoBinding;
import com.cubes.komentar.pavlovic.data.domain.News;
import com.cubes.komentar.pavlovic.ui.main.video.VideoAdapter;
import com.cubes.komentar.pavlovic.ui.tools.listener.VideoListener;
import com.squareup.picasso.Picasso;

public class RvItemVideo implements RecyclerViewItemVideo {

    private final News video;
    private final VideoListener videoListener;


    public RvItemVideo(News video, VideoListener videoListener) {
        this.video = video;
        this.videoListener = videoListener;
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

        bindingVideo.imageViewPlay.setOnClickListener(view -> videoListener.onVideoClicked(video));
    }
}
