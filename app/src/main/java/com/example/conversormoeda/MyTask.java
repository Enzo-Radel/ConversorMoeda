package com.example.conversormoeda;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyTask extends AsyncTask<String, Integer, String> {

    TextView output;
    double valor;
    String moedaOrigem, moedaDestino;
    boolean excecao;

    public MyTask(TextView output, double valor, String moedaOrigem, String moedaDestino) {
        this.output = output;
        this.valor = valor;
        this.moedaOrigem = moedaOrigem;
        this.moedaDestino = moedaDestino;
        this.excecao = false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String stringURL = strings[0];
        if(this.moedaDestino.equalsIgnoreCase("BTC")){
            this.excecao = true;
            this.moedaDestino = this.moedaOrigem;
            this.moedaOrigem = "BTC";
        }
        stringURL = stringURL + this.moedaOrigem+"-"+this.moedaDestino;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        StringBuffer buffer = null;

        try {
            URL url = new URL(stringURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader  = new BufferedReader(inputStreamReader);
            buffer = new StringBuffer();
            String line = "";
            while((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            double resultado = Double.parseDouble(jsonObject.getJSONObject(this.moedaOrigem+this.moedaDestino).getString("bid")) * this.valor;
            if (this.excecao) {
                resultado = this.valor/resultado;
                this.excecao = false;
            }
            this.output.setText(String.valueOf(resultado));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
