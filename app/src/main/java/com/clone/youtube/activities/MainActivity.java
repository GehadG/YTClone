package com.clone.youtube.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.clone.youtube.fragments.YoutubeVideoFragment;
import com.clone.youtube.fragments.YoutubeVideoPlayerFragment;
import com.clone.youtube.youtubeclone.R;

public class MainActivity extends AppCompatActivity implements YoutubeVideoFragment.OnListFragmentInteractionListener ,YoutubeVideoPlayerFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        this.getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new YoutubeVideoFragment(), null).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem mSearch = menu.findItem(R.id.action_search);
        final SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Fragment f = new YoutubeVideoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("query", query);
                f.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, f).addToBackStack(null).commit();
                mSearchView.setIconified(true);
                mSearchView.clearFocus();
                mSearch.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }
}
