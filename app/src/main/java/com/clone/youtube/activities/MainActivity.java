package com.clone.youtube.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.clone.youtube.fragments.YoutubeVideoFragment;
import com.clone.youtube.youtubeclone.R;

public class MainActivity extends AppCompatActivity implements YoutubeVideoFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new YoutubeVideoFragment(), "tag").commit();
    }
}
