package com.clone.youtube.api;

import com.clone.youtube.models.CommentSearchResult;
import com.clone.youtube.models.YoutubeSearchResult;
import com.clone.youtube.util.Converter;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;


public class YoutubeClient {
    private static YoutubeClient ourInstance;
    private YouTube youtube;
    private YoutubeService service;

    private YoutubeClient() {
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {

            }

        }).setApplicationName("youtube-test-app").build();
        service = new YoutubeService(youtube);
    }

    public static YoutubeClient getInstance() {
        if (ourInstance == null) {
            ourInstance = new YoutubeClient();
        }
        return ourInstance;
    }

    public Observable<YoutubeSearchResult> searchWithQuery(final String query, final String pageToken) {
        return Observable.create(new Observable.OnSubscribe<YoutubeSearchResult>() {
            @Override
            public void call(Subscriber<? super YoutubeSearchResult> subscriber) {
                try {
                    subscriber.onNext(Converter.convertToVideoModel(service.search(query, pageToken)));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });


    }

    public Observable<CommentSearchResult> getComments(final String videoId, final String pageToken) {
        return Observable.create(new Observable.OnSubscribe<CommentSearchResult>() {
            @Override
            public void call(Subscriber<? super CommentSearchResult> subscriber) {
                try {
                    subscriber.onNext(Converter.convertToCommentModel(service.getComments(videoId, pageToken)));
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }

}
