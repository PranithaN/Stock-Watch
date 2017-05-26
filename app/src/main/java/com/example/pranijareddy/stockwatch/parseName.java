package com.example.pranijareddy.stockwatch;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pranijareddy on 3/12/2017.
 */

public class parseName {
    int count;

    private MainActivity mainActivity;

    public parseName(MainActivity ma) {
        this.mainActivity = ma;
    }

    public ArrayList<String> parse(String s) {

        ArrayList<String> stockList = new ArrayList<>();

        try {
            JSONArray jObjMain = new JSONArray(s);
            count = jObjMain.length();
            String line;

            for (int i = 0; i < jObjMain.length(); i++) {
                JSONObject jCountry = (JSONObject) jObjMain.get(i);
                String symbol = jCountry.getString("company_symbol");
                String company = jCountry.getString("company_name");
                line = symbol + "-" + company;
                stockList.add(line);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return stockList;
    }
}
