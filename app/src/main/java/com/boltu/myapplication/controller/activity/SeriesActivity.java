package com.boltu.myapplication.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.boltu.myapplication.adapter.SeriesAdapter;
import com.boltu.myapplication.controller.GlobalController;
import com.boltu.myapplication.model.series.SeriesListModel;
import com.sports.bettercricketway.R;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SeriesActivity extends AppCompatActivity {
    GlobalController globalController;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);

        globalController = new GlobalController(this);
        recyclerView = findViewById(R.id.series_recycler);
        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(llm);

        List<SeriesListModel> seriesListModels = globalController.retrieveSeries();
        Collections.reverse(seriesListModels);

        SeriesAdapter adapter = new SeriesAdapter(this, seriesListModels);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onBackPressed() {
        globalController.NextIntent(MainActivity.class);
        finish();
    }
}