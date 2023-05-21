package com.test.appcuaca;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class RetrofitActivity
        extends AppCompatActivity {
    private TextView tv_city, tv_Temperature, tv_Weathercode, tvWindspeed, tvLongitude, tvLatitude, tv_date;
    private TextView tv_day1, tv_day2, tv_day3, tv_day4, tv_day5, tv_day6;
    private TextView tv_Weathercode1, tv_Weathercode2, tv_Weathercode3, tv_Weathercode4, tv_Weathercode5, tv_Weathercode6;
    private ImageView iv_day1, iv_day2, iv_day3, iv_day4, iv_day5, iv_day6;
    private ImageView iv_icon;

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
    public class WeatherData {
        @SerializedName("current_weather")
        private CurrentWeather currentweather;
        @SerializedName("daily")
        private Daily daily;
        private String latitude, windspeed,longitude;
        private int[] weathercode;

        //Method getter
        public String getLatitude() {
            return latitude;
        }

        public String getLongitude () {
            return longitude;
        }

        public String getWindspeed(){
            return windspeed;
        }

        public CurrentWeather getCurrent_weather() {
            return currentweather;
        }

        public Daily getDaily() {
            return daily;
        }
    }
    public class Daily {
        @SerializedName("time")
        private List<String> time;

        @SerializedName("weathercode")
        private List<String> weather_code;
        public List<String> getTime() {
            return time;
        }

        public List<String> getWeather_code() {
            return weather_code;
        }
    }
    public class CurrentWeather {
        @SerializedName("windspeed")
        private String windspeed;

        @SerializedName("temperature")
        private String temperature;

        @SerializedName("weathercode")
        private String weathercode;

        public String getWindspeed() {
            return windspeed;
        }

        public String getTemperature() {
            return temperature;
        }

        public String getWeathercode() {
            return weathercode;
        }
    }
    public interface WeatherService {
        @GET("forecast") // Sesuaikan dengan endpoint API Open-Meteo
        Call<WeatherData> getWeatherData(@Query("latitude") double latitude, @Query("longitude") double longitude,
                                         @Query("daily") String daily, @Query("current_weather") boolean currentWeather,
                                         @Query("timezone") String timezone);
    }
    public void getWeather(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.open-meteo.com/v1/") // Ganti dengan base URL API Open-Meteo
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        WeatherService service = retrofit.create(WeatherService.class);
        double latitude = -7.98, longitude = 112.63;
        String daily = "", timezone = "auto";
        boolean currentWeather = true;
        Call<WeatherData> call = service.
                getWeatherData(
                        latitude, longitude, daily, currentWeather, timezone);
        call.enqueue(new Callback<WeatherData>() {

            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                if (response.isSuccessful()) {
                    tvLatitude.setText(response.body().getLatitude());
                    tvLongitude.setText(response.body().getLongitude());
                    tvWindspeed.setText(response.body().getCurrent_weather().getWindspeed() + " knot");
                    tv_Temperature.setText(response.body().getCurrent_weather().getTemperature() + "\u00B0" + "C");

                    tv_date.setText(response.body().getDaily().getTime().get(0));

                    weatherCodeHandler(response.body().getCurrent_weather().getWeathercode(),0);

                    for (int i = 1; i <= 6; i++) {

                        String time = response.body().getDaily().getTime().get(i);
                        // Process the retrieved time as needed
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
                } else {

                }
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {

            }
        });
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
        }else  if (code.equals("80")||code.equals("81")||code.equals("82")||code.equals("95")||code.equals("96")||code.equals("99")){
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
