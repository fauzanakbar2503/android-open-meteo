package com.test.appcuaca;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class VolleyActivity
        extends AppCompatActivity {
    private TextView tv_city, tv_Temperature, tv_Weathercode, tvWindspeed, tvLongitude, tvLatitude, tv_date;
    private TextView tv_day1, tv_day2, tv_day3, tv_day4, tv_day5, tv_day6;
    private TextView tv_Weathercode1, tv_Weathercode2, tv_Weathercode3, tv_Weathercode4, tv_Weathercode5, tv_Weathercode6;
    private ImageView iv_day1, iv_day2, iv_day3, iv_day4, iv_day5, iv_day6;
    private ImageView iv_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_app);

        tv_city = findViewById(R.id.tv_city);
        tv_Temperature = findViewById(R.id.tv_temperature);
        tv_Weathercode = findViewById(R.id.tv_weatherCode);
        tvWindspeed = findViewById(R.id.tv_windspeed);
        tvLatitude = findViewById(R.id.tv_latitude);
        tvLongitude = findViewById(R.id.tv_longitude);

        iv_icon = findViewById(R.id.iv_icon);
        tv_date = findViewById(R.id.tv_date);
        tv_day1 = findViewById(R.id.tv_day1);
        tv_day2 = findViewById(R.id.tv_day2);
        tv_day3 = findViewById(R.id.tv_day3);
        tv_day4 = findViewById(R.id.tv_day4);
        tv_day5 = findViewById(R.id.tv_day5);
        tv_day6 = findViewById(R.id.tv_day6);
        iv_day1 = findViewById(R.id.iv_day1);
        iv_day2 = findViewById(R.id.iv_day2);
        iv_day3 = findViewById(R.id.iv_day3);
        iv_day4 = findViewById(R.id.iv_day4);
        iv_day5 = findViewById(R.id.iv_day5);
        iv_day6 = findViewById(R.id.iv_day6);
        tv_Weathercode1 = findViewById(R.id.tvWeathercode1);
        tv_Weathercode2 = findViewById(R.id.tvWeathercode2);
        tv_Weathercode3 = findViewById(R.id.tvWeathercode3);
        tv_Weathercode4 = findViewById(R.id.tvWeathercode4);
        tv_Weathercode5 = findViewById(R.id.tvWeathercode5);
        tv_Weathercode6 = findViewById(R.id.tvWeathercode6);
        getWeather();
    }

    private void getWeather() {
        String url = "https://api.open-meteo.com/v1/forecast?latitude=-7.98&longitude=112.63&daily=weathercode&current_weather=true&timezone=auto";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String degree = "\u00B0";
                    String lat_coordinate = response.getString("latitude");
                    String lang_coordinate = response.getString("longitude");

                    JSONObject current_weather = response.getJSONObject("current_weather");

                    String temperature = current_weather.getString("temperature");
                    String windspeed = current_weather.getString("windspeed");
                    String weathercode = current_weather.getString("weathercode");
                    String curr_date = current_weather.getString("time");

                    tv_Temperature.setText(temperature + degree + "C");
                    tvWindspeed.setText(windspeed + " knot");
                    tvLatitude.setText(lat_coordinate);
                    tvLongitude.setText(lang_coordinate);
                    tv_date.setText(curr_date.substring(0,10));
                    weatherCodeHandler(weathercode, 0);
                    JSONObject daily = response.getJSONObject("daily");
                    JSONArray tanggal = daily.getJSONArray("time");
                    JSONArray kode_weather = daily.getJSONArray("weathercode");

                    for (int i = 1; i <= kode_weather.length(); i++) {
                        String code = kode_weather.getString(i);
                        weatherCodeHandler(code, i);

                        String time = tanggal.getString(i);

                        // Menampilkan waktu
                        switch (i){
                            case 1:
                                tv_day1.setText(time);
                            case 2:
                                tv_day2.setText(time);
                            case 3:
                                tv_day3.setText(time);
                            case 4:
                                tv_day4.setText(time);
                            case 5:
                                tv_day5.setText(time);
                            case 6:
                                tv_day6.setText(time);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Gagal menampilkan data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
    private void weatherCodeHandler(String code, int i){
        if (code.equals("45")||code.equals("48")){
            switch (i){
                case 0:
                    tv_Weathercode.setText("Foggy");
                    iv_icon.setImageResource(R.drawable.fog);
                case 1:
                    tv_Weathercode1.setText("Foggy");
                    iv_day1.setImageResource(R.drawable.fog);
                case 2:
                    tv_Weathercode2.setText("Foggy");
                    iv_day2.setImageResource(R.drawable.fog);
                case 3:
                    tv_Weathercode3.setText("Foggy");
                    iv_day3.setImageResource(R.drawable.fog);
                case 4:
                    tv_Weathercode4.setText("Foggy");
                    iv_day4.setImageResource(R.drawable.fog);
                case 5:
                    tv_Weathercode5.setText("Foggy");
                    iv_day5.setImageResource(R.drawable.fog);
                case 6:
                    tv_Weathercode6.setText("Foggy");
                    iv_day6.setImageResource(R.drawable.fog);
            }
        } else  if (code.equals("2") || code.equals("3") ){
            switch (i){
                case 0:
                    tv_Weathercode.setText("Partly Cloud");
                    iv_icon.setImageResource(R.drawable.partly_cloud_2);
                case 1:
                    tv_Weathercode1.setText("Partly Cloud");
                    iv_day1.setImageResource(R.drawable.partly_cloud_2);
                case 2:
                    tv_Weathercode2.setText("Partly Cloud");
                    iv_day2.setImageResource(R.drawable.partly_cloud_2);
                case 3:
                    tv_Weathercode3.setText("Partly Cloud");
                    iv_day3.setImageResource(R.drawable.partly_cloud_2);
                case 4:
                    tv_Weathercode4.setText("Partly Cloud");
                    iv_day4.setImageResource(R.drawable.partly_cloud_2);
                case 5:
                    tv_Weathercode5.setText("Partly Cloud");
                    iv_day5.setImageResource(R.drawable.partly_cloud_2);
                case 6:
                    tv_Weathercode6.setText("Partly Cloud");
                    iv_day6.setImageResource(R.drawable.partly_cloud_2);
            }
        } else  if (code.equals("1") || code.equals("0") ){
            switch (i){
                case 0:
                    tv_Weathercode.setText("Mainly Clear");
                    iv_icon.setImageResource(R.drawable.mainly_clear);
                case 1:
                    tv_Weathercode1.setText("Mainly Clear");
                    iv_day1.setImageResource(R.drawable.mainly_clear);
                case 2:
                    tv_Weathercode2.setText("Mainly Clear ");
                    iv_day2.setImageResource(R.drawable.mainly_clear);
                case 3:
                    tv_Weathercode3.setText("Mainly Clear ");
                    iv_day3.setImageResource(R.drawable.mainly_clear);
                case 4:
                    tv_Weathercode4.setText("Mainly Clear ");
                    iv_day4.setImageResource(R.drawable.mainly_clear);
                case 5:
                    tv_Weathercode5.setText("Mainly Clear ");
                    iv_day5.setImageResource(R.drawable.mainly_clear);
                case 6:
                    tv_Weathercode6.setText("Mainly Clear ");
                    iv_day6.setImageResource(R.drawable.mainly_clear);
            }
        }else  if (code.equals("51")||code.equals("53")||code.equals("55")||code.equals("56")||code.equals("57")||
                code.equals("61")||code.equals("63")||code.equals("65")||code.equals("66")||code.equals("67")){
            switch (i){
                case 0:
                    tv_Weathercode.setText("Raining");
                    iv_icon.setImageResource(R.drawable.raining);
                case 1:
                    tv_Weathercode1.setText("Raining");
                    iv_day1.setImageResource(R.drawable.raining);
                case 2:
                    tv_Weathercode2.setText("Raining");
                    iv_day2.setImageResource(R.drawable.raining);
                case 3:
                    tv_Weathercode3.setText("Raining");
                    iv_day3.setImageResource(R.drawable.raining);
                case 4:
                    tv_Weathercode4.setText("Raining");
                    iv_day4.setImageResource(R.drawable.raining);
                case 5:
                    tv_Weathercode5.setText("Raining");
                    iv_day5.setImageResource(R.drawable.raining);
                case 6:
                    tv_Weathercode6.setText("Raining");
                    iv_day6.setImageResource(R.drawable.raining);
            }
        }else  if (code.equals("80")||code.equals("81")||code.equals("82")||
                code.equals("95")||code.equals("96")||code.equals("99")){
            switch (i){
                case 0:
                    tv_Weathercode.setText("Raining Thunder");
                    iv_icon.setImageResource(R.drawable.thunder);
                case 1:
                    tv_Weathercode1.setText("Raining Thunder");
                    iv_day1.setImageResource(R.drawable.thunder);
                case 2:
                    tv_Weathercode2.setText("Raining Thunder");
                    iv_day2.setImageResource(R.drawable.thunder);
                case 3:
                    tv_Weathercode3.setText("Raining Thunder");
                    iv_day3.setImageResource(R.drawable.thunder);
                case 4:
                    tv_Weathercode4.setText("Raining Thunder");
                    iv_day4.setImageResource(R.drawable.thunder);
                case 5:
                    tv_Weathercode5.setText("Raining Thunder");
                    iv_day5.setImageResource(R.drawable.thunder);
                case 6:
                    tv_Weathercode6.setText("Raining Thunder");
                    iv_day6.setImageResource(R.drawable.thunder);
            }
        } else {
            switch (i){
                case 0:
                    tv_Weathercode.setText("Snowy");
                    iv_icon.setImageResource(R.drawable.snowing);
                case 1:
                    tv_Weathercode1.setText("Snowy");
                    iv_day1.setImageResource(R.drawable.snowing);
                case 2:
                    tv_Weathercode2.setText("Snowy");
                    iv_day2.setImageResource(R.drawable.snowing);
                case 3:
                    tv_Weathercode3.setText("Snowy");
                    iv_day3.setImageResource(R.drawable.snowing);
                case 4:
                    tv_Weathercode4.setText("Snowy");
                    iv_day4.setImageResource(R.drawable.snowing);
                case 5:
                    tv_Weathercode5.setText("Snowy");
                    iv_day5.setImageResource(R.drawable.snowing);
                case 6:
                    tv_Weathercode6.setText("Snowy");
                    iv_day6.setImageResource(R.drawable.snowing);
            }
        }
    }
}