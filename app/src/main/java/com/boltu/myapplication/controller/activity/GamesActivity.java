package com.boltu.myapplication.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;
import com.boltu.myapplication.adapter.GamesAdapter;
import com.boltu.myapplication.adapter.ViewPageAdapter;
import com.boltu.myapplication.controller.GlobalController;
import com.boltu.myapplication.controller.activity.fragment.AllGamesFragment;
import com.boltu.myapplication.controller.activity.fragment.SeriesGamesFragment;
import com.boltu.myapplication.model.games.MatchListModel;
import com.google.android.material.tabs.TabLayout;
import com.sports.bettercricketway.R;

import java.util.List;

public class GamesActivity extends AppCompatActivity {
    GlobalController globalController;

    ViewPager viewPager;
    TabLayout tabLayout;
    AllGamesFragment allGamesFragment;
    SeriesGamesFragment seriesGamesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        globalController = new GlobalController(this);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(GamesActivity.this.getSupportFragmentManager(),0);
        seriesGamesFragment = new SeriesGamesFragment();
        viewPageAdapter.addFragment(seriesGamesFragment, "Series Games");
        allGamesFragment = new AllGamesFragment();
        viewPageAdapter.addFragment(allGamesFragment, "All Games");
        viewPager.setAdapter(viewPageAdapter);



    }
    @Override
    public void onBackPressed() {
        globalController.NextIntent(MainActivity.class);
        finish();
    }
}

