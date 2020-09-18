package com.example.projetamio;

import android.util.Log;

import java.util.TimerTask;

public class LogEveryMoment extends TimerTask {

    private static int number = 0;

    @Override
    public void run() {
        Log.d("LogEvetyMoment","Log : " + LogEveryMoment.number);
        LogEveryMoment.number++;
    }
}
