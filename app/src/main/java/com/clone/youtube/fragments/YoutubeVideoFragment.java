package com.clone.youtube.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clone.youtube.models.YoutubeSearchResult;
import com.clone.youtube.models.YoutubeVideo;
import com.clone.youtube.util.YoutubeClient;
import com.clone.youtube.youtubeclone.R;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class YoutubeVideoFragment extends Fragment {
    private static final String TAG = YoutubeVideoFragment.class.getSimpleName();
    private static final String ARG_COLUMN_COUNT = "column-count";
    private VideoListAdapter adapter ;
    private Subscription subscription;
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
private List<YoutubeVideo> videos;
    public YoutubeVideoFragment() {
    }

    @SuppressWarnings("unused")
    public static YoutubeVideoFragment newInstance(int columnCount) {
        YoutubeVideoFragment fragment = new YoutubeVideoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_youtubevideo_list, container, false);
        adapter=new VideoListAdapter(mListener);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        recyclerView.setAdapter(adapter);
     //       recyclerView.setAdapter(new VideoListAdapter(DummyContent.ITEMS, mListener));
        }
        loadInitVideos();
        return view;
    }

    private void loadInitVideos() {

        subscription = YoutubeClient.getInstance()
                .searchWithQuery(null,null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<YoutubeSearchResult>() {
                    @Override public void onCompleted() {
                        Log.d(TAG, "In onCompleted()");
                    }

                    @Override public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "In onError()");
                    }

                    @Override public void onNext(YoutubeSearchResult searchResult) {
                        Log.d(TAG, "In onNext()");
                        adapter.addVideos(searchResult.getVideos());
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
public void onDestroy(){
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

    public interface OnListFragmentInteractionListener {

    }
}
