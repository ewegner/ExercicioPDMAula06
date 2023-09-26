package com.example.exerciciopdmaula06;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConsultaCep extends AppCompatActivity {

    private EditText cep;
    private EditText logradouro;
    private EditText complemento;
    private EditText bairro;
    private EditText cidade;
    private EditText uf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_cep);

        cep         = (EditText) findViewById(R.id.cep);
        logradouro  = (EditText) findViewById(R.id.logradouro);
        complemento = (EditText) findViewById(R.id.complemento);
        bairro      = (EditText) findViewById(R.id.bairro);
        cidade      = (EditText) findViewById(R.id.cidade);
        uf          = (EditText) findViewById(R.id.uf);
    }


    public void consultarCepInserido(View view) {
        String cep_inserido = cep.getText().toString();
        new HttpAsyncTask().execute(cep_inserido);
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected String doInBackground(String... params) {
            try {
                URL url = new URL("https://viacep.com.br/ws/" + params[0] + "/json");
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
                    return builder.toString();
                }
            } catch (Exception ex) {
                Log.e("ConsultaCepError", ex.toString());
            }
            return null;
        }
        public void onPostExecute(String result) {
            dialog.dismiss();

            if(result != null) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String log  = obj.getString("logradouro");
                    String com = obj.getString("complemento");
                    String bai     = obj.getString("bairro");
                    String cid      = obj.getString("localidade");
                    String unf          = obj.getString("uf");

                    logradouro.setText(log);
                    complemento.setText(com);
                    bairro.setText(bai);
                    cidade.setText(cid);
                    uf.setText(unf);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        protected void onPreExecute() {
            dialog = new ProgressDialog(ConsultaCep.this);
            dialog.show();
        }
    }
}