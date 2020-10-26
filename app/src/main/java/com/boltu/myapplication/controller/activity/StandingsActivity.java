package com.boltu.myapplication.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boltu.myapplication.adapter.StandingsAdapter;
import com.boltu.myapplication.controller.GlobalController;
import com.boltu.myapplication.model.series.SeriesListModel;
import com.boltu.myapplication.model.standings.StandingsModel;
import com.boltu.myapplication.model.standings.TeamStandingModel;
import com.sports.bettercricketway.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StandingsActivity extends AppCompatActivity {
    GlobalController globalController;
    RecyclerView recyclerView;
    SeriesListModel currentSeries;
    TextView seriesName;
    TextView seriesStatus;
    TextView seriesStart;
    TextView seriesEnd;
    TextView txtNoAvailable;
    ImageView seriesLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);
        globalController = new GlobalController(this);

        seriesName = findViewById(R.id.standings_series_name);
        seriesStatus = findViewById(R.id.standings_series_status);
        seriesStart = findViewById(R.id.standings_series_start);
        seriesEnd = findViewById(R.id.standings_series_end);
        seriesLogo = findViewById(R.id.standings_series_logo);
        txtNoAvailable = findViewById(R.id.txtNoAvailable);

        recyclerView = findViewById(R.id.standings_recycler);
        GridLayoutManager grid = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(grid);

        StandingsModel standing = globalController.retrieveStanding();
        List<SeriesListModel> series = globalController.retrieveSeries();
        for(int i = 0 ; i < series.size() ; i++){
            if(series.get(i).getId().toString().equalsIgnoreCase(globalController.getDefaultSeries())){
                this.currentSeries = series.get(i);
                seriesName.setText(this.currentSeries.getName());
                seriesStatus.setText(this.currentSeries.getStatus());
                seriesStart.setText(this.currentSeries.getStartDateTime());
                seriesEnd.setText(this.currentSeries.getEndDateTime());
                Picasso.get().load(this.currentSeries.getShieldImageUrl()).into(seriesLogo);
                break;
            }
        }
        List<TeamStandingModel> teamStandingModels = standing.getTeams();
        StandingsAdapter adapter = new StandingsAdapter(this,teamStandingModels);
        recyclerView.setAdapter(adapter);

        if (teamStandingModels.size() == 0) {
            txtNoAvailable.setVisibility(View.VISIBLE);
        } else {
            txtNoAvailable.setVisibility(View.GONE);
        }
    }
    @Override
    public void onBackPressed() {
        globalController.NextIntent(MainActivity.class);
        finish();
    }
}

