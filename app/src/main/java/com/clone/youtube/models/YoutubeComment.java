package com.clone.youtube.models;

public class YoutubeComment {
    private String authorName;
    private String authorImage;
    private long publishedAt;
    private String comment;

    public YoutubeComment(String authorName, String authorImage, long publishedAt, String comment) {
        this.authorName = authorName;
        this.authorImage = authorImage;
        this.publishedAt = publishedAt;
        this.comment = comment;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public long getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(long publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
