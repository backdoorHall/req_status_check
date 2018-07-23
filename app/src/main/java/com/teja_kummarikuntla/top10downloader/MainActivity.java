package com.teja_kummarikuntla.top10downloader;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Starting Asyinc TAsk");
        DownloadData downloadData = new DownloadData();
        downloadData.execute("url goes here");
        Log.d(TAG, "onCreate: done");

    }
    private class DownloadData extends AsyncTask<String, Void, String>{
        private static final String TAG = "DownloadData";
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: The Parameter is : "+s);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: Starts with "+strings[0]);
            String rssFeed = donwlaodXML(strings[0]);
            if(rssFeed ==null){
                Log.e(TAG, "doInBackground: Error Downlaoding rsssFeed" );
            }
            return rssFeed;

        }

        private String donwlaodXML(String urlPath){
            StringBuilder xmlResult = new StringBuilder();
            try {
                URL url = new URL(urlPath);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                int response = httpURLConnection.getResponseCode();
                Log.d(TAG, "donwlaodXML: The response code is" + response);
//                InputStream in = httpURLConnection.getInputStream();
//                InputStreamReader inputStreamReader = new InputStreamReader(in);
//                BufferedReader reader = new BufferedReader(inputStreamReader);
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                int lineread;
                char[] bufferRead = new char[500];
                while (true){
                    lineread = reader.read(bufferRead);
                    if(lineread <0){
                        break;
                    }
                    if(lineread>0){
                        xmlResult.append(String.copyValueOf(bufferRead,0,lineread));
                    }
                }
                reader.close();
                return xmlResult.toString();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}

