package com.example.projetamio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import static com.example.projetamio.MainActivity.PREFS_NAME;

public class MyBootBroadcastReceiver extends BroadcastReceiver {

    public MyBootBroadcastReceiver(){
        super();
        Log.d(this.getClass().getName(), "Controleur initialisé");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyBootBroadcastReceiver", "Le broadcast est bien arrivé");
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        if ( settings.getBoolean("startAtBoot", false)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(new Intent(context, DatareceiverFromServerService.class));
                Log.d("MyBootBroadcastReceiver", "IF");
            } else {
                context.startService(new Intent(context, DatareceiverFromServerService.class));
                Log.d("MyBootBroadcastReceiver", "Else");
            }
        }
        else{
            Log.d("MyBootBroadcastReceiver","Le service ne devait pas être démaré");
        }
    }
}
