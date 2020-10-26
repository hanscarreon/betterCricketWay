package com.boltu.myapplication.model.players;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlayerListModel {
    @SerializedName("players")
    private List<PlayerDetailsModel> players = null;

    public PlayerListModel(List<PlayerDetailsModel> players) {
        this.players = players;
    }

    public List<PlayerDetailsModel> getPlayers() {
        return players;
    }
}
