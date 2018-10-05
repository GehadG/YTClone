package com.clone.youtube.util;

import com.clone.youtube.models.YoutubeSearchResult;
import com.clone.youtube.models.YoutubeVideo;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static YoutubeSearchResult convertToVideoModel(SearchListResponse response){
        List<YoutubeVideo> videos = new ArrayList<>();
        for(SearchResult i:response.getItems()){
            videos.add(new YoutubeVideo(
                    i.getId().getVideoId(),
                    i.getSnippet().getThumbnails().getHigh().getUrl(),
                    i.getSnippet().getTitle(),
                    i.getSnippet().getDescription(),
                    i.getSnippet().getPublishedAt().getValue()
            ));
        }
        return new YoutubeSearchResult(response.getNextPageToken(),videos);
    }
}
