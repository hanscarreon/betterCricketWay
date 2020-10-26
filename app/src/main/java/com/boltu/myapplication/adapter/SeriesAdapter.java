package com.boltu.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.boltu.myapplication.controller.GlobalController;
import com.boltu.myapplication.controller.activity.MainActivity;
import com.boltu.myapplication.model.series.SeriesListModel;
import com.sports.bettercricketway.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder> {
    GlobalController globalController;
    CountDownTimer countDownTimer;
    Context context;
    List<SeriesListModel> seriesListModelList;
    SeriesListModel seriesListModel;
    int flag;
    public class SeriesViewHolder extends RecyclerView.ViewHolder {
        TextView seriesName;
        TextView status;
        TextView startDate;
        TextView seriesId;
        TextView endDate;
        ImageView seriesLogo;
        public SeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            seriesName = itemView.findViewById(R.id.series_name);
            seriesId = itemView.findViewById(R.id.series_id);
            status = itemView.findViewById(R.id.series_status);
            startDate = itemView.findViewById(R.id.series_start);
            endDate = itemView.findViewById(R.id.series_end);
            seriesLogo = itemView.findViewById(R.id.series_logo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context,seriesId.getText().toString(),Toast.LENGTH_SHORT).show();
                    globalController.startLoading();
                    loop();
                    globalController.clearContents();
                    globalController.setDefaultSeries(seriesId.getText().toString());
                    globalController.saveSeries();
                    globalController.saveSeriesGames();
                    globalController.saveStandings();
                    globalController.saveTeams();
                    globalController.saveAllGames();
                }
            });
        }
    }

    public SeriesAdapter(Context context, List<SeriesListModel> seriesListModelList){
        this.context = context;
        globalController = new GlobalController(this.context);
        this.seriesListModelList = seriesListModelList;
        flag = 0;
    }
    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.series_list, parent, false);
        return new SeriesViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SeriesViewHolder holder, int position) {
        seriesListModel = seriesListModelList.get(position);
        holder.seriesName.setText(seriesListModel.getName());
        holder.seriesId.setText(seriesListModel.getId().toString());
        holder.status.setText(seriesListModel.getStatus());
        holder.startDate.setText("Start: "+ seriesListModel.getStartDateTime());
        holder.endDate.setText("End: "+seriesListModel.getEndDateTime());
        Picasso.get().load(seriesListModel.getShieldImageUrl()).into(holder.seriesLogo);
    }

    @Override
    public int getItemCount() {
        return seriesListModelList.size();
    }

    public void loop(){
        loadIntent();
    }

    public void loadIntent(){
        countDownTimer = new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if(globalController.retrieveGames() == null ||
                        globalController.retrieveAllGames() == null ||
                        globalController.retrieveSeries() == null ||
                        globalController.retrieveTeams() == null ||
                        globalController.retrieveStanding() == null){
                    errorChecker();
                    loop();
                }else{
                    globalController.stopLoading();
                    globalController.NextIntent(MainActivity.class);
                }

            }
        }.start();
    }

    public void errorHolder(){
        if(flag == 0){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Failed To Connect, Try To Restart the Application!");
            alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
            flag++;
        }
    }


    public void errorChecker(){
        if(globalController.getErrors(GlobalController.SERIES_ERR).contains("timeout")){
            globalController.saveSeries();
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Requesting for Series");
            globalController.setErrors(GlobalController.SERIES_ERR,"");
        }else if(!globalController.getErrors(GlobalController.SERIES_ERR).isEmpty()){
            errorHolder();
        }
        if(globalController.getErrors(GlobalController.GAMES_SEASON_LEAGUE_ERR).contains("timeout")){
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Requesting for Series Games");
            globalController.saveSeriesGames();
            globalController.setErrors(GlobalController.GAMES_SEASON_LEAGUE_ERR,"");
        }else if(!globalController.getErrors(GlobalController.GAMES_SEASON_LEAGUE_ERR).isEmpty()){
            errorHolder();
        }
        if(globalController.getErrors(GlobalController.STANDINGS_ERR).contains("timeout")){
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Requesting for Standings");
            globalController.saveStandings();
            globalController.setErrors(GlobalController.STANDINGS_ERR,"");
        }else if(!globalController.getErrors(GlobalController.STANDINGS_ERR).isEmpty()){
            errorHolder();
        }
        if(globalController.getErrors(GlobalController.TEAMS_SEASON_LEAGUE_ERR).contains("timeout")){
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Requesting for Teams");
            globalController.saveTeams();
            globalController.setErrors(GlobalController.TEAMS_SEASON_LEAGUE_ERR,"");
        }else if(!globalController.getErrors(GlobalController.TEAMS_SEASON_LEAGUE_ERR).isEmpty()){
            errorHolder();
        }
        if(globalController.getErrors(GlobalController.GAMES_ALL_ERR).contains("timeout")){
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Requesting for All Games");
            globalController.saveAllGames();
            globalController.setErrors(GlobalController.GAMES_ALL_ERR,"");
        }else if(!globalController.getErrors(GlobalController.GAMES_ALL_ERR).isEmpty()){
            errorHolder();
        }
    }

}
