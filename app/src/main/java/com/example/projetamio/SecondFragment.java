//package com.example.projetamio;
//
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.util.JsonReader;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.navigation.fragment.NavHostFragment;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//
//import static androidx.core.content.ContextCompat.getSystemService;
//
//public class SecondFragment extends Fragment implements DownloadCallback {
//
//    private boolean downloading;
////    private NetworkFragment networkFragment;
//    private ListLampe listLampe;
//
//    @Override
//    public View onCreateView(
//            LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState
//    ) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_second, container, false);
//    }
//
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//    }
//
//    @Override
//    public void updateFromDownload(Object result){
//        if (result instanceof String) {
//            String resultString = (String)result;
//            if (resultString.contains("HTTP error code:") || resultString.contains("no protocol:")) {
//                /**CharSequence text = "Erreur lors du chargement des données";
//                int duration = Toast.LENGTH_SHORT;
//                Toast toast = Toast.makeText(this.getBaseContext(), text, duration);
//                toast.show();*/
//                Log.w(this.getClass().getName(), result.toString());
//            }
//            else{
//                Log.d(this.getClass().getName(), result.toString());
//                JsonReader reader = null;
//                try {
//                    reader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(resultString.getBytes()), "UTF-8"));
//                    reader.beginObject();
//                    DonneesLampe lampe = null;
//                    while (reader.hasNext()) {
//                        if (reader.nextName() == "data") {
//                            reader.beginArray();
//                            while (reader.hasNext()) {
//                                reader.beginObject();
//                                while (reader.hasNext()) {
//                                    String name = reader.nextName();
//                                    if (name.equals("name")) {
//                                        lampe = this.listLampe.getLampe(reader.nextString());
//                                    } else if (name.equals("light")) {
//                                        assert lampe != null;
//                                        lampe.addEtat(reader.nextLong());
//                                    } else {
//                                        reader.skipValue();
//                                    }
//                                }
//                                reader.endObject();
//                            }
//                            reader.endArray();
//                        }
//                        else {
//                            reader.skipValue();
//                        }
//                    }
//                    reader.endObject();
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//
//                } finally {
//                    assert reader != null;
//                    try {
//                        reader.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//    }
//
//    @Override
//    public NetworkInfo getActiveNetworkInfo() {
//        ConnectivityManager connectivityManager =
//                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        assert connectivityManager != null;
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        return networkInfo;
//    }
//
//    @Override
//    public void onProgressUpdate(int progressCode, int percentComplete) {
//        switch(progressCode) {
//            // You can add UI behavior for progress updates here.
//            case DownloadCallback.Progress.ERROR:
//                /**CharSequence text = "Erreur lors du chargement des données";
//                int duration = Toast.LENGTH_SHORT;
//                Toast toast = Toast.makeText(this.getBaseContext(), text, duration);
//                toast.show();*/
//                Log.e(this.getClass().getName(), "Error during data loading");
//                break;
//            case DownloadCallback.Progress.CONNECT_SUCCESS:
//                break;
//            case DownloadCallback.Progress.GET_INPUT_STREAM_SUCCESS:
//                break;
//            case DownloadCallback.Progress.PROCESS_INPUT_STREAM_IN_PROGRESS:
//                break;
//            case DownloadCallback.Progress.PROCESS_INPUT_STREAM_SUCCESS:
//                break;
//        }
//    }
//
//    @Override
//    public void finishDownloading() {
//        this.downloading = false;
//        if (this.networkFragment != null) {
//            this.networkFragment.cancelDownload();
//        }
//    }
//}