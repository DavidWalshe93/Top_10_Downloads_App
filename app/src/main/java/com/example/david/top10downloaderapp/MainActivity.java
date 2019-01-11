package com.example.david.top10downloaderapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: starting Asynctask");
        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");
        Log.d(TAG, "onCreate: done");
    }

    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadData";

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: starts with " + strings[0]);
            String rssFeed = downloadXML(strings[0]);
            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Error downloaded");
            }
            return "rssFeed";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: parameter is " + s);
        }

        private String downloadXML(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: The response code was " + response);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charRead;                               // Size of read size on url connection
                char[] inputBuffer = new char[500];         // Buffer of data to read url connection data to, size 500 chars
                while (true) {                              // Continue reading until the all data is read from the connection
                    charRead = reader.read(inputBuffer);    // Read the url connection for data, add to inputBuffer and assign size of data read to charRead.
                    if (charRead < 0) {                     // If size of read is less then 0 i.e. -1 then terminate the read as it has no more data to read.
                        break;
                    }
                    if (charRead > 0) {                     // If data was read then add it to the xmlResult.
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charRead));     // Convert the chars into a string to append to the string builder.
                    }
                }
            } catch (MalformedURLException e) {
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
            }
            return null;
        }
    }
}

