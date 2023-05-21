package com.test.appcuaca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page);

        Button btn_volley = findViewById(R.id.btn_volley);
        Button btn_retrofit = findViewById(R.id.btn_retrofit);

        //Button untuk Volley
        btn_volley.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VolleyActivity.class);
            startActivity(intent);
        });

        //Button untuk Retrofit
        btn_retrofit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RetrofitActivity.class);
            startActivity(intent);
        });
    }
}