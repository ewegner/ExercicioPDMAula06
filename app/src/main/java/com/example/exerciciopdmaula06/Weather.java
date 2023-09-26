package com.example.exerciciopdmaula06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Weather extends AppCompatActivity {

    private TextView mediaTemperatura;
    private TextView mediaUmidade;
    private TextView mediaOrvalho;
    private TextView mediaPressao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mediaTemperatura = (TextView) findViewById(R.id.mediaTemperatura);
        mediaUmidade     = (TextView) findViewById(R.id.mediaUmidade);
        mediaOrvalho     = (TextView) findViewById(R.id.mediaOrvalho);
        mediaPressao     = (TextView) findViewById(R.id.mediaPressao);
    }
}