package com.example.yh.eagle_eye;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class weather extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
    }

    public void onClickRun(View view) {


        //String api_answer = api.doInBackground();

        //weather_info.setText(api_answer);
        new RetrieveFeedTask().execute();

    }


    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;
        public String api_result;

        protected void onPreExecute() {
            //progressBar.setVisibility(View.VISIBLE);
            //weather_info.setText("");


        }

        protected String doInBackground(Void... urls) {
            // Do some validation here

            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?id=2193734&appid=a9e7262d2b2567a13a498dccc80203fd");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            //progressBar.setVisibility(View.GONE);
            Log.i("INFO", response);
            api_result = response;

            TextView weather_info = (TextView) findViewById(R.id.weather_info);

            try {
                JSONObject reader = new JSONObject(response);

                JSONObject sys = reader.getJSONObject("coord");
                String longitude = sys.getString("lon");
                String latitude = sys.getString("lat");
                weather_info.setText("Longitude: "+longitude + "\n" + "Latitude: " + latitude);

            } catch (final JSONException e){

            }


        }

    }
}
