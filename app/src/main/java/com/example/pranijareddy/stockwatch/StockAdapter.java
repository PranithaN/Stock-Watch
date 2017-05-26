package com.example.pranijareddy.stockwatch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import java.util.List;

/**
 * Created by Pranijareddy on 3/7/2017.
 */

public class StockAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<Stock> stockList;
    private MainActivity mainAct;

    public StockAdapter(List<Stock> sList, MainActivity ma) {
        this.stockList = sList;
        mainAct = ma;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_list, parent, false);

       itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Stock stock = stockList.get(position);
        double chan=stock.getChange();
        if(chan>=0){
            holder.symbol.setTextColor(Color.GREEN);
        holder.symbol.setText(stock.getSymbol());
            holder.company.setTextColor(Color.GREEN);
        holder.company.setText(stock.getCompany());
            holder.lastIndexPrice.setTextColor(Color.GREEN);
        holder.lastIndexPrice.setText(String.format("%.2f",stock.getIndexPrice()));
            holder.priceChange.setTextColor(Color.GREEN);
        holder.priceChange.setText(String.format("%.2f",stock.getChange()));
            holder.changePercentage.setTextColor(Color.GREEN);
        holder.changePercentage.setText("( "+String.format("%.2f",stock.getPercentage())+"% )");
            holder.imageView.setImageResource(R.drawable.ic_action_up);
       holder.imageView.setColorFilter(Color.GREEN);}
        if(chan<0){
            holder.symbol.setTextColor(Color.RED);
            holder.symbol.setText(stock.getSymbol());
            holder.company.setTextColor(Color.RED);
            holder.company.setText(stock.getCompany());
            holder.lastIndexPrice.setTextColor(Color.RED);
            holder.lastIndexPrice.setText(String.format("%.2f",stock.getIndexPrice()));
            holder.priceChange.setTextColor(Color.RED);
            holder.priceChange.setText(String.format("%.2f",stock.getChange()));
            holder.changePercentage.setTextColor(Color.RED);
            holder.changePercentage.setText("( "+String.format("%.2f",(stock.getPercentage()))+"% )");
            holder.imageView.setImageResource(R.drawable.ic_action_down);


            holder.imageView.setColorFilter(Color.RED);}

    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }
}
