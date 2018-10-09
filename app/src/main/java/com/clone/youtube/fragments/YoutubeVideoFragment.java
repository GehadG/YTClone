package com.clone.youtube.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clone.youtube.adapters.VideoListAdapter;
import com.clone.youtube.api.YoutubeClient;
import com.clone.youtube.models.YoutubeSearchResult;
import com.clone.youtube.youtubeclone.R;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class YoutubeVideoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = YoutubeVideoFragment.class.getSimpleName();
    private static final String QUERY_KEY = "query";
    SwipeRefreshLayout mSwipeRefreshLayout;
    private VideoListAdapter adapter;
    private Subscription subscription;
    private String nextPageToken;
    private String currentQuery;
    private OnListFragmentInteractionListener mListener;

    public YoutubeVideoFragment() {
    }

    @Override
    public void onResume() {
        if (currentQuery != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(currentQuery);
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Youtube Clone");
        }
        nextPageToken = null;
        adapter.clear();
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            currentQuery = getArguments().getString(QUERY_KEY);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(currentQuery);
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Youtube Clone");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_youtubevideo_list, container, false);
        adapter = new VideoListAdapter(mListener, getActivity().getSupportFragmentManager());
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
                if (mSwipeRefreshLayout.isRefreshing())
                    return;
                if (!recyclerView.canScrollVertically(1)) {
                    loadVideos(currentQuery);

                }
            }
        });
        onRefresh();

        return view;
    }

    private void loadVideos(String q) {

        subscription = YoutubeClient.getInstance()
                .searchWithQuery(q, nextPageToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<YoutubeSearchResult>() {
                    @Override
                    public void onCompleted() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(YoutubeSearchResult searchResult) {


                        adapter.addVideos(searchResult.getVideos());
                        nextPageToken = searchResult.getNextPageToken();

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
    public void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        nextPageToken = null;
        mSwipeRefreshLayout.setRefreshing(true);
        adapter.clear();
        loadVideos(currentQuery);
    }

    public interface OnListFragmentInteractionListener {

    }
}
