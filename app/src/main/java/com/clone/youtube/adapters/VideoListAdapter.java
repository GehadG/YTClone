package com.clone.youtube.adapters;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.clone.youtube.fragments.YoutubeVideoFragment.OnListFragmentInteractionListener;
import com.clone.youtube.fragments.YoutubeVideoPlayerFragment;
import com.clone.youtube.models.YoutubeVideo;
import com.clone.youtube.youtubeclone.R;
import com.ocpsoft.pretty.time.PrettyTime;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    private static final String VIDEO_ID = "videoId";
    private static final String VIDEO_TITLE = "videoTitle";
    private final OnListFragmentInteractionListener mListener;
    private List<YoutubeVideo> mValues;
    private FragmentManager fragmentManager;

    public VideoListAdapter(OnListFragmentInteractionListener listener, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        mListener = listener;
    }

    public void clear() {
        if (mValues != null)
            mValues.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_youtubevideo, parent, false);
        return new ViewHolder(view);
    }

    public void addVideos(List<YoutubeVideo> videos) {
        if (mValues == null) {
            mValues = new ArrayList<>();
        }
        mValues.addAll(videos);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.videoTitle.setText(mValues.get(position).getTitle());
        holder.vDetails.setText(createDetails(mValues.get(position)));
        Picasso.get()
                .load("http://img.youtube.com/vi/" + mValues.get(position).getId() + "/maxresdefault.jpg")
                .placeholder(R.drawable.progress_loader)
                .error(R.drawable.progress_loader)
                .resize(Resources.getSystem().getDisplayMetrics().widthPixels, 0)
                .noFade()
                .into(holder.thumbnail, new Callback() {

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(holder.mItem.getThumbnail())
                                .placeholder(R.drawable.progress_loader)
                                .error(R.drawable.progress_loader)
                                .resize(Resources.getSystem().getDisplayMetrics().widthPixels, 0)
                                .noFade()
                                .into(holder.thumbnail);
                    }


                });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = new YoutubeVideoPlayerFragment();
                Bundle bundle = new Bundle();
                bundle.putString(VIDEO_ID, holder.mItem.getId());
                bundle.putString(VIDEO_TITLE, holder.mItem.getTitle());
                f.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, f).addToBackStack(null).commit();
            }
        });
    }

    private String createDetails(YoutubeVideo youtubeVideo) {
        PrettyTime p = new PrettyTime();
        String buffer = " · ";
        return youtubeVideo.getChannelTitle() + buffer + p.format(new Date(youtubeVideo.getPublishedAt()));
    }

    @Override
    public int getItemCount() {
        if (mValues == null) {
            mValues = new ArrayList<>();
        }
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView videoTitle;
        public final TextView vDetails;
        public final ImageView thumbnail;
        public YoutubeVideo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            videoTitle = (TextView) view.findViewById(R.id.videoTitle);
            vDetails = (TextView) view.findViewById(R.id.videoDetails);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }

    }
}
