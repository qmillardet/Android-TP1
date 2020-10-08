package com.example.projetamio;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.TimerTask;

public class LogEveryMoment extends TimerTask {

    private static int number = 0;
    private Context context;

    public void setContext(Context con){
        this.context = con;
    }

    @Override
    public void run() {
        Log.d("LogEvetyMoment","Log : " + LogEveryMoment.number);
        LogEveryMoment.number++;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, DatareceiverFromServerService.class));
        } else {
            context.startService(new Intent(context, DatareceiverFromServerService.class));
        }

    }
}
