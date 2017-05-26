package com.example.pranijareddy.stockwatch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pranijareddy on 3/7/2017.
 */

public class Stock implements Comparable<Stock> {
    private String symbol;
    private String company;
    private double indexPrice;
    private double change;
    private double percentage;
    public Stock(String sy, String com){
        symbol=sy;
        company=com;
    }
    public Stock(String sy, String com, double pr, double c, double p) {
        symbol = sy;
        company = com;
        indexPrice = pr;
        change = c;
        percentage = p;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getIndexPrice() {
        return indexPrice;
    }

    public void setIndexPrice(double indexPrice) {
        this.indexPrice = indexPrice;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }



    @Override
    public int compareTo(Stock o) {
        return symbol.compareTo(o.getSymbol());
    }
}
