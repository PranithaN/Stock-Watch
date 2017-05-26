package com.example.pranijareddy.stockwatch;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pranijareddy on 3/10/2017.
 */

public class parseData {
    int count;
    public ArrayList<Stock> parse(String s) {

        ArrayList<Stock> stockList = new ArrayList<>();
        int sp = s.indexOf("\n//");
        String company = s.substring(0, sp);
        String pdata = s.substring(sp);
        String[] part =pdata.split("//");
        String data = part[1];

        try {
            JSONArray jObjMain = new JSONArray(data);
            count = jObjMain.length();


            for (int i = 0; i < jObjMain.length(); i++) {
                JSONObject jCountry = (JSONObject) jObjMain.get(i);
                String symbol = jCountry.getString("t");
                double lastTradePrice =Double.parseDouble( jCountry.getString("l"));
                double priceChange = Double.parseDouble(jCountry.getString("c"));
                double percentageChange = Double.parseDouble(jCountry.getString("cp"));
                stockList.add(new Stock(symbol,company,lastTradePrice,priceChange,percentageChange));

            }
            return stockList;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }
}
