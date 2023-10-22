package com.example.exerciciopdmaula06;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Weather extends AppCompatActivity {

    private TextView mediaTemperatura;
    private TextView mediaUmidade;
    private TextView mediaOrvalho;
    private TextView mediaPressao;
    private ListView todosDados;
    List<Map<String,Object>> lista;

    String [] de = {"temp", "umid", "orva", "pres", "velo", "dire", "date"};

    int [] para = {R.id.txtTemp, R.id.txtHum, R.id.txtDew, R.id.txtPres, R.id.txtSpe, R.id.txtDir, R.id.txtDate};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mediaTemperatura = (TextView) findViewById(R.id.mediaTemperatura);
        mediaUmidade     = (TextView) findViewById(R.id.mediaUmidade);
        mediaOrvalho     = (TextView) findViewById(R.id.mediaOrvalho);
        mediaPressao     = (TextView) findViewById(R.id.mediaPressao);
        todosDados       = (ListView) findViewById(R.id.todasMedicoes);

        new Weather.HttpAsyncTask().execute();
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected String doInBackground(String... params) {
            try {
                URL url = new URL("https://ghelfer.net/la/weather.json");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                int status = urlConnection.getResponseCode();

                if (status == 200) {
                    InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder builder = new StringBuilder();

                    String inputString;

                    while ((inputString = bufferedReader.readLine()) != null) {
                        builder.append(inputString);
                    }

                    urlConnection.disconnect();
                    Log.d("WeatherResult", builder.toString());
                    return builder.toString();
                }
            } catch (Exception ex) {
                Log.e("WeatherError", ex.toString());
            }
            return null;
        }
        public void onPostExecute(String result) {
            dialog.dismiss();

            if(result != null) {
                try {
                    double mediaTemp = 0.00;
                    double mediaUmid = 0.00;
                    double mediaOrva = 0.00;
                    double mediaPres = 0.00;


                    JSONObject obj = new JSONObject(result);
                    JSONArray arrayTemps = obj.getJSONArray("weather");
                    lista = new ArrayList<>();
                    Map<String,Object> titles = new HashMap<>();
                    titles.put(de[0], "Temp");
                    titles.put(de[1], "Humi");
                    titles.put(de[2], "Dew");
                    titles.put(de[3], "Pres");
                    titles.put(de[4], "Spd");
                    titles.put(de[5], "Dir");
                    titles.put(de[6], "Date");
                    lista.add(titles);

                    for (int i = 0; i < arrayTemps.length(); i++) {
                        JSONObject jsonTemps = arrayTemps.getJSONObject(i);
                        String mediaT = jsonTemps.get("temperature").toString();
                        mediaTemp += Double.valueOf(mediaT);

                        String mediaU = jsonTemps.get("humidity").toString();
                        mediaUmid += Double.valueOf(mediaU);

                        String mediaO = jsonTemps.get("dewpoint").toString();
                        mediaOrva += Double.valueOf(mediaO);

                        String mediaP = jsonTemps.get("pressure").toString();
                        mediaPres += Double.valueOf(mediaP);

                        Map<String,Object> mapa = new HashMap<>();
                        mapa.put(de[0], jsonTemps.get("temperature").toString());
                        mapa.put(de[1], jsonTemps.get("humidity").toString());
                        mapa.put(de[2], jsonTemps.get("dewpoint").toString());
                        mapa.put(de[3], jsonTemps.get("pressure").toString());
                        mapa.put(de[4], jsonTemps.get("speed").toString());
                        mapa.put(de[5], jsonTemps.get("direction").toString());
                        mapa.put(de[6], jsonTemps.get("datetime").toString());
                        lista.add(mapa);
                    }

                    DecimalFormat df = new DecimalFormat("0.00");
                    DecimalFormat dfm = new DecimalFormat("0,000.00");

                    mediaTemperatura.setText("Média de temperaturas: " + String.valueOf(df.format(mediaTemp / arrayTemps.length())));
                    mediaUmidade.setText("Média de umidade: " + String.valueOf(df.format(mediaUmid / arrayTemps.length())));
                    mediaOrvalho.setText("Média de orvalho: " + String.valueOf(df.format(mediaOrva / arrayTemps.length())));
                    mediaPressao.setText("Média de pressão: " + String.valueOf(dfm.format(mediaPres / arrayTemps.length())));

                    SimpleAdapter adapter;

                    adapter = new MeuAdaptador(Weather.this, lista, R.layout.uma_linha, de, para);
                    todosDados.setAdapter(adapter);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(Weather.this);
            dialog.show();
        }
    }
}