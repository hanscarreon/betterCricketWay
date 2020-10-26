package com.boltu.myapplication.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.boltu.myapplication.controller.GlobalController;
import com.sports.bettercricketway.R;

public class MainActivity extends AppCompatActivity {
    TextView games;
    TextView teams;
    TextView series;
    TextView standings;
    GlobalController globalController;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        globalController = new GlobalController(this);

        games = findViewById(R.id.games);
        teams = findViewById(R.id.teams);
        series = findViewById(R.id.series);
        standings = findViewById(R.id.standings);
        flag = 0;

        games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalController.NextIntent(GamesActivity.class);
                finish();
            }
        });
        teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalController.NextIntent(TeamsActivity.class);
                finish();
            }
        });
        series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalController.NextIntent(SeriesActivity.class);
                finish();
            }
        });
        standings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalController.NextIntent(StandingsActivity.class);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if(flag == 1){
            finish();
        }else{
            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();
            flag++;
        }
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + flag);
    }
}