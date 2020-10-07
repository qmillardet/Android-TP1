package com.example.projetamio;

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

public class MainActivity extends FragmentActivity implements DownloadCallback {

    private static int status = 0;
    public static final String PREFS_NAME = "MyPrefsFile";
    // Keep a reference to the NetworkFragment, which owns the AsyncTask object
    // that is used to execute network ops.
    private NetworkFragment networkFragment;

    // Boolean telling us whether a download is in progress, so we don't trigger overlapping
    // downloads with consecutive button clicks.
    private boolean downloading = false;

    private ListLampe listLampe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), "https://qmillardet.github.io/API/android/example.json");
        this.listLampe = new ListLampe();

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
                        networkFragment.startDownload();
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

    @Override
    public void updateFromDownload(Object result){
        if (result instanceof String) {
            String resultString = (String)result;
            if (resultString.contains("HTTP error code:") || resultString.contains("no protocol:")) {
                CharSequence text = "Erreur lors du chargement des données";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this.getBaseContext(), text, duration);
                toast.show();
                Log.w(this.getClass().getName(), result.toString());
            }
            else{
                Log.d(this.getClass().getName(), result.toString());
                JsonReader reader = null;
                try {
                    reader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(resultString.getBytes()), "UTF-8"));
                    reader.beginObject();
                    DonneesLampe lampe = null;
                    while (reader.hasNext()) {
                        if (reader.nextName() == "data") {
                            reader.beginArray();
                            while (reader.hasNext()) {
                                reader.beginObject();
                                while (reader.hasNext()) {
                                    String name = reader.nextName();
                                    if (name.equals("name")) {
                                        lampe = this.listLampe.getLampe(reader.nextString());
                                    } else if (name.equals("light")) {
                                        assert lampe != null;
                                        lampe.addEtat(reader.nextLong());
                                    } else {
                                        reader.skipValue();
                                    }
                                }
                                reader.endObject();
                            }
                            reader.endArray();
                        }
                        else {
                            reader.skipValue();
                        }
                    }
                    reader.endObject();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {

                } finally {
                    assert reader != null;
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch(progressCode) {
            // You can add UI behavior for progress updates here.
            case Progress.ERROR:
                CharSequence text = "Erreur lors du chargement des données";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this.getBaseContext(), text, duration);
                toast.show();
                break;
            case Progress.CONNECT_SUCCESS:
                break;
            case Progress.GET_INPUT_STREAM_SUCCESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
                break;
            case Progress.PROCESS_INPUT_STREAM_SUCCESS:
                break;
        }
    }

    @Override
    public void finishDownloading() {
        downloading = false;
        if (networkFragment != null) {
            networkFragment.cancelDownload();
        }
    }
}