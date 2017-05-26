package com.example.pranijareddy.stockwatch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Pranijareddy on 3/7/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView symbol;
    public TextView company;
    public TextView lastIndexPrice;
    public TextView priceChange;
    public TextView changePercentage;
    public ImageView imageView;

    public MyViewHolder(View view) {
        super(view);
        symbol=(TextView) view.findViewById(R.id.stock_symbol);
        company=(TextView) view.findViewById(R.id.companyName);
        lastIndexPrice=(TextView) view.findViewById(R.id.lastIndexPrice);
        priceChange=(TextView) view.findViewById(R.id.priceChange);
        changePercentage=(TextView) view.findViewById(R.id.changePercentage);
        imageView=(ImageView) view.findViewById(R.id.imageView);
    }
}
