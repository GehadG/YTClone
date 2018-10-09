package com.clone.youtube.models;

import java.util.List;

public class CommentSearchResult {
    private String nextPageToken;
    private List<YoutubeComment> comments;

    public CommentSearchResult(String nextPageToken, List<YoutubeComment> comments) {
        this.nextPageToken = nextPageToken;
        this.comments = comments;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<YoutubeComment> getComments() {
        return comments;
    }

    public void setComments(List<YoutubeComment> comments) {
        this.comments = comments;
    }
}
