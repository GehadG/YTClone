package com.clone.youtube.fragments;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.clone.youtube.fragments.YoutubeVideoFragment.OnListFragmentInteractionListener;
import com.clone.youtube.models.YoutubeVideo;
import com.clone.youtube.youtubeclone.R;
import com.ocpsoft.pretty.time.PrettyTime;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;
    private  List<YoutubeVideo> mValues;

    public VideoListAdapter( OnListFragmentInteractionListener listener) {

        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_youtubevideo, parent, false);
        return new ViewHolder(view);
    }
    public void addVideos(List<YoutubeVideo> videos){
        if(mValues==null){
            mValues=new ArrayList<>();
        }
        mValues.addAll(videos);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.videoTitle.setText(mValues.get(position).getTitle());
        holder.vDetails.setText(createDetails(mValues.get(position)));
        System.out.println("size : "+ Resources.getSystem().getDisplayMetrics().widthPixels);
        Picasso.get()
                .load("http://img.youtube.com/vi/"+mValues.get(position).getId()+"/maxresdefault.jpg")
                .resize(Resources.getSystem().getDisplayMetrics().widthPixels,0)
                .into(holder.thumbnail);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    //TODO : show video
                }
            }
        });
    }

    private String createDetails(YoutubeVideo youtubeVideo) {
        PrettyTime p = new PrettyTime();
        String buffer = " · ";
        return youtubeVideo.getChannelTitle()+buffer+p.format(new Date(youtubeVideo.getPublishedAt()));
    }

    @Override
    public int getItemCount() {
        if(mValues==null){
            mValues=new ArrayList<>();
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
            thumbnail=(ImageView)view.findViewById(R.id.thumbnail);
        }

    }
}
