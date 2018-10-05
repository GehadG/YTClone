package com.clone.youtube.util;

import com.clone.youtube.models.YoutubeSearchResult;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;

import java.io.IOException;

class YoutubeClient {
    private static YoutubeClient ourInstance ;
    //TODO : Define config or properties file to contain the api key

    private YouTube youtube;
    private YoutubeService service;
    private YoutubeClient() {
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {

            }

        }).setApplicationName("youtube-test-app").build();
        service=new YoutubeService(youtube);
    }

    static YoutubeClient getInstance() {
        if (ourInstance == null)
        {
            ourInstance=new YoutubeClient();
        }
            return ourInstance;
    }

    public YoutubeSearchResult searchWithQuery(String query, String pageToken){
        return Converter.convertToVideoModel(service.search(query,pageToken));
    }

}
