package com.clone.youtube.models;

import java.util.List;

public class YoutubeSearchResult {
    private String nextPageToken;
    private List<YoutubeVideo> videos;

    public YoutubeSearchResult(String nextPageToken, List<YoutubeVideo> videos) {
        this.nextPageToken = nextPageToken;
        this.videos = videos;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<YoutubeVideo> getVideos() {
        return videos;
    }

    public void setVideos(List<YoutubeVideo> videos) {
        this.videos = videos;
    }
}
