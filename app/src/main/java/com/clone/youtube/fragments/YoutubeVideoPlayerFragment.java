package com.clone.youtube.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clone.youtube.adapters.CommentsAdapter;
import com.clone.youtube.api.YoutubeClient;
import com.clone.youtube.models.CommentSearchResult;
import com.clone.youtube.youtubeclone.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class YoutubeVideoPlayerFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String VIDEO_ID = "videoId";
    private static final String VIDEO_TITLE ="videoTitle" ;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private OnListFragmentInteractionListener mListener;
private String videoId;
private String videoTitle;
    private CommentsAdapter adapter ;
    private Subscription subscription;
    private String nextPageToken;

    public YoutubeVideoPlayerFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            videoId = getArguments().getString(VIDEO_ID);
            videoTitle= getArguments().getString(VIDEO_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_list, container, false);
       adapter=new CommentsAdapter(mListener);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(mSwipeRefreshLayout.isRefreshing())
                    return;
                if (!recyclerView.canScrollVertically(1)) {
                    loadComments();

                }
            }
        });
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(videoTitle);
        YouTubePlayerView youtubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youtubePlayerView);

        youtubePlayerView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(@NonNull final YouTubePlayer initializedYouTubePlayer) {
                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        initializedYouTubePlayer.loadVideo(videoId, 0);
                    }
                });
            }
        }, true);
 loadComments();
 mSwipeRefreshLayout.setEnabled(false);
        return view;
    }

    private void loadComments() {
        subscription = YoutubeClient.getInstance()
                .getComments(videoId,nextPageToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentSearchResult>() {
                    @Override public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override public void onError(Throwable e) {
                        e.printStackTrace();

                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override public void onNext(CommentSearchResult searchResult) {


                        adapter.addComments(searchResult.getComments());
                        nextPageToken=searchResult.getNextPageToken();

                    }
                });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {

    }


    public interface OnListFragmentInteractionListener {
    }
}
