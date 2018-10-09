package com.clone.youtube.util;

import com.clone.youtube.models.CommentSearchResult;
import com.clone.youtube.models.YoutubeComment;
import com.clone.youtube.models.YoutubeSearchResult;
import com.clone.youtube.models.YoutubeVideo;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static YoutubeSearchResult convertToVideoModel(SearchListResponse response) {
        List<YoutubeVideo> videos = new ArrayList<>();
        for (SearchResult i : response.getItems()) {
            videos.add(new YoutubeVideo(
                    i.getId().getVideoId(),
                    i.getSnippet().getThumbnails().getHigh().getUrl(),
                    i.getSnippet().getTitle(),
                    i.getSnippet().getDescription(),
                    i.getSnippet().getPublishedAt().getValue(),
                    i.getSnippet().getChannelTitle()
            ));
        }
        return new YoutubeSearchResult(response.getNextPageToken(), videos);
    }

    public static CommentSearchResult convertToCommentModel(CommentThreadListResponse comments) {
        List<YoutubeComment> commentList = new ArrayList<>();
        for (CommentThread i : comments.getItems()) {
            commentList.add(new YoutubeComment(
                    i.getSnippet().getTopLevelComment().getSnippet().getAuthorDisplayName(),
                    i.getSnippet().getTopLevelComment().getSnippet().getAuthorProfileImageUrl(),
                    i.getSnippet().getTopLevelComment().getSnippet().getPublishedAt().getValue(),
                    i.getSnippet().getTopLevelComment().getSnippet().getTextDisplay()
            ));
        }
        return new CommentSearchResult(comments.getNextPageToken(), commentList);
    }
}
