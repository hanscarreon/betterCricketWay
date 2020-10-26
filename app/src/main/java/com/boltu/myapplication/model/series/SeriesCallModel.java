package com.boltu.myapplication.model.series;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeriesCallModel {
    @SerializedName("series")
    private List<SeriesListModel> series = null;

    public SeriesCallModel(List<SeriesListModel> series) {
        this.series = series;
    }

    public List<SeriesListModel> getSeries() {
        return series;
    }
}
