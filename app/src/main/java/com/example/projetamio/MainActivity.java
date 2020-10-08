package com.example.projetamio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity{

    private static int status = 0;
    public static final String PREFS_NAME = "MyPrefsFile";
    // Keep a reference to the NetworkFragment, which owns the AsyncTask object
    // that is used to execute network ops.
//    private NetworkFragment networkFragment;

    // Boolean telling us whether a download is in progress, so we don't trigger overlapping
    // downloads with consecutive button clicks.
    private boolean downloading = false;

    private ListLampe listLampe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onStart() {
        super.onStart();


        Intent service = new Intent(this, MainService.class);

        // Gestion du bouton toggle

        Button on = findViewById(R.id.toggle);
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView status = findViewById(R.id.TV2);
                switch (MainActivity.status){
                    case 0 :
                        status.setText("En cours");
                        startService(service);
                        MainActivity.status = 1;
                        downloading = true;
                        break;
                    case 1 :
                        status.setText("Arrêté");
                        stopService(service);
                        MainActivity.status = 0;
                        break;
                    default:
                        MainActivity.status = 10;
                }
            }
        });

        CheckBox startAtBoot = findViewById(R.id.startAtBoot);
        startAtBoot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(this.getClass().getName(), "changement d'état" + isChecked );
                String etat = "";
                if (isChecked){
                    etat = "coché";
                }
                else{
                    etat = "décoché";
                }
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("startAtBoot", isChecked);
                editor.commit();
            }
        });
    }
}