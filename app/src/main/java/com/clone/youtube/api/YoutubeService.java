package com.clone.youtube.api;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.SearchListResponse;

import java.io.IOException;

public class YoutubeService {
    private static final String API_KEY = "AIzaSyCwbGziupadfpxuaTv72BsKZxOAQQDDj9A";
    private YouTube youtube;
    public YoutubeService(YouTube youTube) {
        this.youtube = youTube;
    }

    public SearchListResponse search(String query , String pageToken)  {
        YouTube.Search.List search = null;
        try {
            search = youtube.search().list("id,snippet");

        search.setKey(API_KEY);
        search.setQ(query);
        search.setMaxResults(25l);
        search.setType("video");
        search.setFields("nextPageToken,items(id/videoId,snippet/title,snippet/description,snippet/thumbnails/high/url,snippet/publishedAt,snippet/channelTitle)");
        if(pageToken!=null){
            search.setPageToken(pageToken);
        }
           return search.execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CommentThreadListResponse getComments(String videoId, String pageToken){
        YouTube.CommentThreads.List search = null;
        try {
            search=youtube.commentThreads().list("snippet,replies");
            search.setKey(API_KEY);
            search.setVideoId(videoId);
            search.setMaxResults(100l);
            if(pageToken!=null){
                search.setPageToken(pageToken);
            }
            return search.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
