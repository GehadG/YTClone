package com.clone.youtube.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clone.youtube.fragments.YoutubeVideoPlayerFragment.OnListFragmentInteractionListener;
import com.clone.youtube.models.YoutubeComment;
import com.clone.youtube.youtubeclone.R;
import com.ocpsoft.pretty.time.PrettyTime;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;
    private  List<YoutubeComment> mValues;
    public CommentsAdapter(OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
     holder.prepareView();

    }
    public void addComments(List<YoutubeComment> comments){
        if(mValues==null){
            mValues=new ArrayList<>();
        }
        mValues.addAll(comments);
        notifyDataSetChanged();
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
        public final TextView mUserName;
        public final TextView mComment;
        public final CircularImageView profilePic;
        public YoutubeComment mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mUserName = (TextView) view.findViewById(R.id.name);
            mComment = (TextView) view.findViewById(R.id.description);
            profilePic=(CircularImageView) view.findViewById(R.id.profile_image);
        }


        public void prepareView() {
            mUserName.setText(createDetails(mItem.getAuthorName(),mItem.getPublishedAt()));
            mComment.setText(mItem.getComment());
            Picasso.get()
                    .load(mItem.getAuthorImage())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .fit()
                    .into(profilePic);

        }
        private String createDetails(String username,long publishedAt) {
            PrettyTime p = new PrettyTime();
            String buffer = " · ";
            return username+buffer+p.format(new Date(publishedAt));
        }
    }
}
