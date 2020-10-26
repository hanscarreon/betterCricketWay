package com.boltu.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.boltu.myapplication.controller.GlobalController;
import com.boltu.myapplication.controller.activity.GamesActivity;
import com.boltu.myapplication.controller.activity.MainActivity;
import com.sports.bettercricketway.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class SplashActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    GlobalController globalController;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        globalController = new GlobalController(this);
        globalController.clearContents();
        globalController.saveSeries();
        globalController.saveSeriesGames();
        globalController.saveStandings();
        globalController.saveTeams();
        globalController.saveAllGames();
        flag = 0;
        loop();

    }

    public void loop() {
        start();
    }

    public void start() {
        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (globalController.retrieveTeams() == null ||
                        globalController.retrieveSeries() == null ||
                        globalController.retrieveStanding() == null ||
                        globalController.retrieveAllGames() == null ||
                        globalController.retrieveGames() == null) {
                    errorChecker();
                    loop();
                } else {
                    globalController.NextIntent(MainActivity.class);
                    finish();
                }
            }
        }.start();

    }

    public void errorHolder(){
        if(flag == 0){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity.this);
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

//    public void kuninIP()
//    {
//
//       Thread thread = new Thread(){
//            public void run(){
//                InetAddress ia = null;
//                try {
//                    ia = InetAddress.getLocalHost();
//                } catch (UnknownHostException e) {
//                    e.printStackTrace();
//                }
//                String str = ia.getHostAddress();
//                System.out.println( "ABOONGAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + str);
//            }
//       };
//        thread.start();
//    }
//    public String getLocalIpAddress() {
//        try {
//            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
//                NetworkInterface intf = en.nextElement();
//                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
//                    InetAddress inetAddress = enumIpAddr.nextElement();
//                    if (!inetAddress.isLoopbackAddress()) {
//                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
//                        Log.i(TAG, "***** IP="+ ip);
//                        return ip;
//                    }
//                }
//            }
//        } catch (SocketException ex) {
//            Log.e(TAG, ex.toString());
//        }
//        return null;
//    }
}