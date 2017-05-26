package com.example.pranijareddy.stockwatch;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Pranijareddy on 3/10/2017.
 */

public class StockNameDownloader extends AsyncTask<String, Void, String> {
    private MainActivity mainActivity;
    private int count;
    private String text;
    private String[] data = {String.valueOf(new String[1])};
    private String API="c465df27d4649b73c447d850a684592a70d39ef6";

    private final String dataURL="http://stocksearchapi.com/api";
    public StockNameDownloader(MainActivity ma) {
        mainActivity = ma;
    }
    @Override
    protected void onPreExecute() {

    }


    @Override
    protected void onPostExecute(String s) {
        parseName p=new parseName(mainActivity);
        if(s==null){
            AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
            TextView tv1=new TextView(mainActivity);
            // tv1.setInputType(InputType.TYPE_CLASS_TEXT);
            tv1.setGravity(Gravity.CENTER_VERTICAL);

            builder.setView(tv1);
            builder.setMessage("Data for this stock is not found");
            builder.setTitle("Symbol Not Found : "+text);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {
            ArrayList<String> stock = p.parse(s) ;
            int i=stock.size();
            try{
                // dialog.dismiss();
                if(i==1){
                    data[0]=stock.get(0).toString();
                    String[] part=data[0].split("-");
                    String sym=part[0];
                    String com=part[1];
                    mainActivity.newSymbol(sym,com);
                }
                else if(i>1){
                    final String[] sArray = new String[i];
                    for (int n = 0; n < i; n++)
                        sArray[n] = stock.get(n);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                    builder.setTitle("Make a Stock selection");
                    // builder.setIcon(R.drawable.icon2);

                    builder.setItems(sArray, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            data[0]=sArray[which].toString();
                            String[] part=data[0].split("-");
                            String sym=part[0];
                            String com=part[1];
                            mainActivity.newSymbol(sym,com);
                        }
                    });

                    builder.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
                    AlertDialog dialog = builder.create();

                    dialog.show();

                }}
            catch (Exception e){

                AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                TextView tv1=new TextView(mainActivity);
                // tv1.setInputType(InputType.TYPE_CLASS_TEXT);
                tv1.setGravity(Gravity.CENTER_VERTICAL);

                builder.setView(tv1);
                builder.setMessage("Something went Wrong please recheck the request"+e);
                builder.setTitle("Incorrect Request");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
       }

    }
    @Override
    protected String doInBackground(String... params) {
        text=params[0];
        Uri.Builder buildURL = Uri.parse(dataURL).buildUpon();
        buildURL.appendQueryParameter("api_key",API);
        buildURL.appendQueryParameter("search_text", params[0]);

        String urlToUse = buildURL.build().toString();
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlToUse);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader((new InputStreamReader(is)));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }

        } catch (Exception e) {

            return null;
        }
        return sb.toString();
    }

}
