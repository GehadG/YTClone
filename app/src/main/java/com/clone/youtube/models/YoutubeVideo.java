package com.clone.youtube.models;

public class YoutubeVideo {
    private String id;
    private String thumbnail;
    private String title;
    private String description;
    private long publishedAt;
    private String channelTitle;

    public YoutubeVideo(String id, String thumbnail, String title, String description, long publishedAt, String channelTitle) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.channelTitle = channelTitle;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(long publishedAt) {
        this.publishedAt = publishedAt;
    }
}
