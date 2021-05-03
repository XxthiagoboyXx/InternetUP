package com.auba.internetup.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.auba.internetup.R;
import com.auba.internetup.data.SecurityPreferences;
import com.auba.internetup.constants.buttonStatus;


import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewHolder mViewholder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    TextView textStatusInternet;
    long startTime = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable(){

        private ViewHolder mViewholder = new ViewHolder();
        private SecurityPreferences mSecurityPreferences;

        @Override
        public void run() {

            this.mViewholder.textStatusInternet = findViewById(R.id.text_status);

            this.mSecurityPreferences = new SecurityPreferences(getApplicationContext());

            String status = this.mSecurityPreferences.getStoredString(buttonStatus.STATUS_KEY);

            if(status.equals(buttonStatus.YES_STATUS)) {
                Context context = getApplicationContext();
                if(internetVerify(context)){
                    this.mViewholder.textStatusInternet.setText("UP");
                }
                else {
                    this.mViewholder.textStatusInternet.setText("Down");
                }

            }
            else{
                this.mViewholder.textStatusInternet.setText("------");
            }

            timerHandler.postDelayed(this,3000);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewholder.textStatusInternet = findViewById(R.id.text_status);
        this.mViewholder.buttonActivate = findViewById(R.id.button_active);

        this.mViewholder.buttonActivate.setOnClickListener(this);

        homeStatus();
        timerHandler.postDelayed(timerRunnable, 0);

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.button_active) {
            this.statusChange();
        }
    }

    public void statusChange(){
        String status = this.mSecurityPreferences.getStoredString(buttonStatus.STATUS_KEY);
        //this.mViewholder.buttonActivate.setText(status);

        if(status.equals("")){
            this.mViewholder.buttonActivate.setText("Desativado");
            this.mSecurityPreferences.storedString(buttonStatus.STATUS_KEY, buttonStatus.NO_STATUS);
        }
        else if(status.equals(buttonStatus.NO_STATUS)) {
            this.mViewholder.buttonActivate.setText("Ativado");
            this.mSecurityPreferences.storedString(buttonStatus.STATUS_KEY,buttonStatus.YES_STATUS);
        }
        else{
            this.mViewholder.buttonActivate.setText("Desativado");
            this.mSecurityPreferences.storedString(buttonStatus.STATUS_KEY,buttonStatus.NO_STATUS);
        }

    }

    public void homeStatus(){
        String status = this.mSecurityPreferences.getStoredString(buttonStatus.STATUS_KEY);
        if(status.equals(buttonStatus.YES_STATUS)){
            status = "Ativado";
        }
        else {
            status = "Desativado";
        }

        this.mViewholder.buttonActivate.setText(status);
    }


    public static boolean internetVerify(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkStatus = cm.getActiveNetworkInfo();

        if( null != networkStatus){
            return true;
        }
        else {
            return false;
        }

    }


    private static class ViewHolder{
        Button buttonActivate;
        TextView textStatusInternet;
    }
}