package com.boltu.myapplication.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.boltu.myapplication.adapter.TeamsAdapter;
import com.boltu.myapplication.controller.GlobalController;
import com.boltu.myapplication.model.teams.TeamsListModel;
import com.sports.bettercricketway.R;

import java.util.List;

public class TeamsActivity extends AppCompatActivity {
    GlobalController globalController;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        globalController = new GlobalController(this);
        recyclerView = findViewById(R.id.teams_recycler);
        GridLayoutManager grid = new GridLayoutManager(TeamsActivity.this,1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(grid);

        List<TeamsListModel> teamsListModelList = globalController.retrieveTeams();
        TeamsAdapter adapter = new TeamsAdapter(this, teamsListModelList);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        globalController.NextIntent(MainActivity.class);
        finish();
    }
}