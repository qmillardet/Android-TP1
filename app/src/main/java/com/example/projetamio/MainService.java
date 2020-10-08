package com.example.projetamio;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

public class MainService extends Service {
    private boolean downloading = false;
//    private NetworkFragment networkFragment;

    public MainService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Function create manually
     */
    @Override
    public void onCreate() {
        LogEveryMoment log = new LogEveryMoment();
        log.setContext(this);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(log, 0, 30*1000);
    }

}