package com.example.projetamio;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MainService extends Service {
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
        Log.d("MainService", "onCreate Function working");
        LogEveryMoment log = new LogEveryMoment();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(log, 0, 30*1000);

    }
}