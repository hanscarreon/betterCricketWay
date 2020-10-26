package com.boltu.myapplication.model.games;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GamesModel {

    @SerializedName("matchList")
    private MatchModel matchList;

        public GamesModel(MatchModel matchList) {
            this.matchList = matchList;
        }

    public MatchModel getMatchList() {
        return matchList;
    }
}