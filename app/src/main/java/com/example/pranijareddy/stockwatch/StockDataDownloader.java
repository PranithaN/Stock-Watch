package com.example.pranijareddy.stockwatch;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Pranijareddy on 3/10/2017.
 */

public class StockDataDownloader extends AsyncTask<String, Void, String> {
    private MainActivity mainActivity;
    private int count;



    private final String dataURL="http://finance.google.com/finance/info";
    public StockDataDownloader(MainActivity ma) {
        mainActivity = ma;
    }
    @Override
    protected void onPreExecute() {

    }


    @Override
    protected void onPostExecute(String s) {
        parseData p=new parseData();
        ArrayList<Stock> stockList = p.parse(s) ;
       mainActivity.loadData(stockList);

    }
    @Override
    protected String doInBackground(String... params) {
        String company=params[1];

        Uri.Builder buildURL = Uri.parse(dataURL).buildUpon();
        buildURL.appendQueryParameter("client","ig");
        buildURL.appendQueryParameter("q", params[0]);

        String urlToUse = buildURL.build().toString();
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            sb.append(company);
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }



        } catch (Exception e) {

            return null;
        }
        return sb.toString();
    }


}
