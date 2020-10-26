package com.boltu.myapplication.model.players;

import com.google.gson.annotations.SerializedName;

public class PlayerDetailsModel {
    @SerializedName("playerId")
    private Integer playerId;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("imageURL")
    private String imageURL;
    @SerializedName("battingStyle")
    private String battingStyle;
    @SerializedName("bowlingStyle")
    private String bowlingStyle;
    @SerializedName("playerType")
    private String playerType;
    @SerializedName("dob")
    private String dob;
    @SerializedName("testDebutDate")
    private String testDebutDate;
    @SerializedName("odiDebutDate")
    private String odiDebutDate;
    @SerializedName("t20DebutDate")
    private String t20DebutDate;
    @SerializedName("bio")
    private String bio;
    @SerializedName("didYouKnow")
    private String didYouKnow;
    @SerializedName("height")
    private String height;

    public PlayerDetailsModel(Integer playerId, String fullName, String firstName, String lastName, String imageURL, String battingStyle, String bowlingStyle, String playerType, String dob, String testDebutDate, String odiDebutDate, String t20DebutDate, String bio, String didYouKnow, String height) {
        this.playerId = playerId;
        this.fullName = fullName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageURL = imageURL;
        this.battingStyle = battingStyle;
        this.bowlingStyle = bowlingStyle;
        this.playerType = playerType;
        this.dob = dob;
        this.testDebutDate = testDebutDate;
        this.odiDebutDate = odiDebutDate;
        this.t20DebutDate = t20DebutDate;
        this.bio = bio;
        this.didYouKnow = didYouKnow;
        this.height = height;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getBattingStyle() {
        return battingStyle;
    }

    public String getBowlingStyle() {
        return bowlingStyle;
    }

    public String getPlayerType() {
        return playerType;
    }

    public String getDob() {
        return dob;
    }

    public String getTestDebutDate() {
        return testDebutDate;
    }

    public String getOdiDebutDate() {
        return odiDebutDate;
    }

    public String getT20DebutDate() {
        return t20DebutDate;
    }

    public String getBio() {
        return bio;
    }

    public String getDidYouKnow() {
        return didYouKnow;
    }

    public String getHeight() {
        return height;
    }
}
