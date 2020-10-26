package com.boltu.myapplication.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.boltu.myapplication.database.CricketApi;
import com.boltu.myapplication.model.games.GamesModel;
import com.boltu.myapplication.model.games.MatchListModel;
import com.boltu.myapplication.model.series.SeriesListModel;
import com.boltu.myapplication.model.series.SeriesModel;
import com.boltu.myapplication.model.standings.StandingsModel;
import com.boltu.myapplication.model.teams.SeriesTeamModel;
import com.boltu.myapplication.model.teams.TeamsListModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class GlobalController {
    public static final String CURRENT_SERIES = "Current_season";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String GAMES_ALL = "games_all";
    public static final String GAMES_SEASON_LEAGUE = "games";
    public static final String TEAMS_SEASON_LEAGUE = "teams";
    public static final String SERIES = "series";
    public static final String STANDINGS = "standings";

    public static final String GAMES_ALL_ERR = "games_all_err";
    public static final String GAMES_SEASON_LEAGUE_ERR = "games_err";
    public static final String TEAMS_SEASON_LEAGUE_ERR = "teams_err";
    public static final String SERIES_ERR = "series_err";
    public static final String STANDINGS_ERR = "standings_err";

    ProgressDialog pdLoading;
    Context context;
    Intent intent;
    Activity activity;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CountDownTimer countDownTimer;


    public String getErrors(String key) {
        return sharedPreferences.getString(key,"");
    }

    public void setErrors(String key, String value) {
        editor.putString(key ,value);
        editor.commit();
    }
    @SuppressLint("CommitPrefEdits")
    public GlobalController(Context context){
        this.pdLoading = new ProgressDialog(context);
        this.context = context;
        this.activity = (Activity) context;
        this.intent = activity.getIntent();
        this.sharedPreferences = this.context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public String getDefaultSeries(){
        return sharedPreferences.getString(CURRENT_SERIES,"2693");
    }
    public void setDefaultSeries(String series){
        editor.putString(CURRENT_SERIES ,series);
        editor.commit();
    }

    public void clearContents(){
        this.editor.clear();
        this.editor.commit();
    }

    //Next Intent w/ Data Function
    public void NextIntent(Class toClass, String data) {
        Intent intent = new Intent(context, toClass);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }
    //Next Intent w/o Data Function
    public void NextIntent(Class toClass) {
        NextIntent(toClass, "");
    }

    //Retrofit Builder Function
    public CricketApi getRetrofitBuilder() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CricketApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CricketApi api = retrofit.create(CricketApi.class);
        return api;
    }

    // Loading Function
    public void startLoading(String text){
        pdLoading.setMessage("\t" + text);
        pdLoading.setCancelable(false);
        pdLoading.show();
    }
    public void startLoading(){
        startLoading("Please Wait...");
    }
    public void stopLoading(){
        pdLoading.dismiss();
    }


    public <T> void saveData(String calling, List<T> object){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        editor.putString(calling ,json);
        editor.commit();
    }
    public <T> void saveData(String calling, T object){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        editor.putString(calling ,json);
        editor.commit();
    }

    public List<MatchListModel> retrieveGames(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(GAMES_SEASON_LEAGUE,"");
        Type type = new TypeToken<List<MatchListModel>>(){}.getType();
        List<MatchListModel> objects = gson.fromJson(json, type);
        return objects;
    }

    public List<MatchListModel> retrieveAllGames(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(GAMES_ALL,"");
        Type type = new TypeToken<List<MatchListModel>>(){}.getType();
        List<MatchListModel> objects = gson.fromJson(json, type);
        return objects;
    }

    public List<SeriesListModel> retrieveSeries(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(SERIES,"");
        Type type = new TypeToken<List<SeriesListModel>>(){}.getType();
        List<SeriesListModel> objects = gson.fromJson(json, type);
        return objects;
    }
    public List<TeamsListModel> retrieveTeams(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(TEAMS_SEASON_LEAGUE,"");
        Type type = new TypeToken<List<TeamsListModel>>(){}.getType();
        List<TeamsListModel> objects = gson.fromJson(json, type);
        return objects;
    }
//
    public StandingsModel retrieveStanding(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(STANDINGS,"");
        Type type = new TypeToken<StandingsModel>(){}.getType();
        StandingsModel objects = gson.fromJson(json, type);
        return objects;
    }

    public void saveSeriesGames(String series){

        Call<GamesModel> call = getRetrofitBuilder().getSeriesGames(series);

        call.enqueue(new Callback<GamesModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<GamesModel> call, retrofit2.Response<GamesModel> response) {
                GamesModel gamesModel = response.body();
                List<MatchListModel> teamsList = gamesModel.getMatchList().getMatches();
                saveData(GAMES_SEASON_LEAGUE, teamsList);
//                setSuccess(PLAYERS,"Players..");
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Games Saved!");
            }
            @Override
            public void onFailure(Call<GamesModel> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                setErrors(GAMES_SEASON_LEAGUE_ERR,t.getMessage());
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + t.getMessage());
            }
        });
    }

    public void saveAllGames(){

        Call<GamesModel> call = getRetrofitBuilder().getAllGames("10","0","0");

        call.enqueue(new Callback<GamesModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<GamesModel> call, retrofit2.Response<GamesModel> response) {
                GamesModel gamesModel = response.body();
                List<MatchListModel> teamsList = gamesModel.getMatchList().getMatches();
                saveData(GAMES_ALL, teamsList);
//                setSuccess(PLAYERS,"Players..");
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa All Games Saved!");
            }
            @Override
            public void onFailure(Call<GamesModel> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                setErrors(GAMES_ALL_ERR,t.getMessage());
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + t.getMessage());
            }
        });
    }

    public void saveSeriesGames(){
        saveSeriesGames(getDefaultSeries());
    }

    public void saveSeries(){

        Call<com.boltu.myapplication.model.series.SeriesModel> call = getRetrofitBuilder().getSeries();

        call.enqueue(new Callback<SeriesModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<com.boltu.myapplication.model.series.SeriesModel> call, retrofit2.Response<com.boltu.myapplication.model.series.SeriesModel> response) {
                com.boltu.myapplication.model.series.SeriesModel seriesModel = response.body();
                List<SeriesListModel> teamsList = seriesModel.getSeriesList().getSeries();
                saveData(SERIES, teamsList);
//                setSuccess(PLAYERS,"Players..");
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Series Saved!");
            }
            @Override
            public void onFailure(Call<com.boltu.myapplication.model.series.SeriesModel> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                setErrors(SERIES_ERR,t.getMessage());
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + t.getMessage());
            }
        });
    }
////
    public void saveTeams(String series){

        Call<SeriesTeamModel> call = getRetrofitBuilder().getTeams(series);

        call.enqueue(new Callback<SeriesTeamModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<SeriesTeamModel> call, retrofit2.Response<SeriesTeamModel> response) {
                SeriesTeamModel standings = response.body();
                List<TeamsListModel> teamsList = standings.getSeriesTeams().getTeams();
                saveData(TEAMS_SEASON_LEAGUE, teamsList);
//                setSuccess(PLAYERS,"Players..");
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Teams Saved!");
            }
            @Override
            public void onFailure(Call<SeriesTeamModel> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                setErrors(TEAMS_SEASON_LEAGUE_ERR,t.getMessage());
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + t.getMessage());
            }
        });
    }
    public void saveTeams(){
        saveTeams(getDefaultSeries());
    }

//
    public void saveStandings(String series){

        Call<StandingsModel> call = getRetrofitBuilder().getStandings(series);

        call.enqueue(new Callback<StandingsModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<StandingsModel> call, retrofit2.Response<StandingsModel> response) {
                StandingsModel gamesModel = response.body();
                saveData(STANDINGS, gamesModel);
//                setSuccess(PLAYERS,"Players..");
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Standings Saved!");
            }
            @Override
            public void onFailure(Call<StandingsModel> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                setErrors(STANDINGS_ERR,t.getMessage());
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + t.getMessage());
            }
        });
    }
    public void saveStandings(){
        saveStandings(getDefaultSeries());
    }
//
//    public void saveTeams(){
//        saveTeams(getDefaultLeague());
//    }
//    public void saveUpcoming(){
//        saveUpcoming(getDefaultLeague());
//    }
//    public void saveHighlights(){
//        saveHighlights(getDefaultLeague());
//    }
}
