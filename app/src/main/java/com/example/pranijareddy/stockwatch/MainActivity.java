package com.example.pranijareddy.stockwatch;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener,View.OnClickListener{

private EditText et;
    private String url="http://www.marketwatch.com/investing/stock/";
    private boolean network=true;
    private List<Stock> stockList = new ArrayList<>();  // Main content is here

    private RecyclerView recyclerView; // Layout's recyclerview

    private StockAdapter mAdapter; // Data to recyclerview adapter
    private SwipeRefreshLayout swiper;

    private DatabaseHandler databaseHandler;
   // private StockDataDownloader stockDataDownloader=new StockDataDownloader();
    private static final int ADD_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycle);

        mAdapter = new StockAdapter(stockList, this);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHandler = new DatabaseHandler(this);
        swiper = (SwipeRefreshLayout) findViewById(R.id.swiper);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefresh();
            }
        });
             loadStockData();

    }

    private void doRefresh() {
        if(networkCheck()){
        List<Stock> list = new ArrayList<>();
        list.addAll(stockList);
        stockList.clear();
        for (Stock c : list) {
            new StockDataDownloader(this).execute(c.getSymbol(),c.getCompany());
        }

        Toast.makeText(this, "Stock Data has been Refreshed", Toast.LENGTH_SHORT).show();}
        else {    AlertDialog.Builder builder = new AlertDialog.Builder(this);
            TextView tv1=new TextView(this);
            tv1.setGravity(Gravity.CENTER_VERTICAL);

            builder.setView(tv1);
            builder.setMessage("Stocks cannot be updated as there is no network");
            builder.setTitle("No Network Found");
            AlertDialog dialog = builder.create();
            dialog.show();}
        swiper.setRefreshing(false);
    }
    public boolean networkCheck() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
         network=true;
        } else {
            network=false;
        }
        return network;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addstock, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add:
                if(networkCheck()==true){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                et = new EditText(this);
                et.setInputType(InputType.TYPE_CLASS_TEXT);
                et.setGravity(Gravity.CENTER_HORIZONTAL);

                builder.setView(et);
                //builder.setIcon(R.drawable);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String s= String.valueOf(et.getText());
                        new StockNameDownloader(MainActivity.this).execute(s);
                    }
                });
                builder.setNegativeButton("NO WAY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                builder.setMessage("Please enter a value:");
                builder.setTitle("Single Input");

                AlertDialog dialog = builder.create();
                dialog.show();}
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    TextView tv1=new TextView(this);
                    tv1.setGravity(Gravity.CENTER_VERTICAL);

                    builder.setView(tv1);
                    builder.setMessage("Stocks cannot be added as there is no network");
                    builder.setTitle("No Network Found");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
               // Toast.makeText(this, "You want to do A", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onClick(View v) {  // click listener called by ViewHolder clicks

        int pos = recyclerView.getChildLayoutPosition(v);
        Stock c = stockList.get(pos);

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url+c.getSymbol()));
        startActivity(i);
    }

    @Override
    public boolean onLongClick(View v) {  // long click listener called by ViewHolder long clicks
        final int pos = recyclerView.getChildLayoutPosition(v);
        Stock s = stockList.get(pos);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                databaseHandler.deleteCountry(stockList.get(pos).getSymbol());
                stockList.remove(pos);
                mAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.setMessage("Delete Stock " + stockList.get(pos).getSymbol() + "?");
        builder.setTitle("Delete Stock");

        AlertDialog dialog = builder.create();
        dialog.show();


        return true;
    }
    public void newSymbol (String sym, String com){
Stock add=new Stock(sym,com);
        addStock(add);

    }
public void addStock(Stock s){
   // Stock st = (Stock) s.getSerializableExtra("S");
    for (Stock c : stockList) {
        if (c.getSymbol().equals(s.getSymbol())) {
            s=new Stock("","");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            TextView tv1=new TextView(this);
            tv1.setGravity(Gravity.CENTER_VERTICAL);
            builder.setView(tv1);
            builder.setMessage("Stock exists");
            builder.setTitle("Duplicate Stock : "+c.getSymbol());
            AlertDialog dialog = builder.create();
            dialog.show();
            break;
        }
    }
if(s.getSymbol().equals("")){}
    else{      databaseHandler.addStock(s);
    new StockDataDownloader(this).execute(s.getSymbol(),s.getCompany());}
}
    public void loadData(ArrayList<Stock> cList) {


        stockList.addAll(cList);
       for(Stock c:stockList){
        Collections.sort(stockList);}
        mAdapter.notifyDataSetChanged();
    }


    public void loadStockData(){
        ArrayList<String[]> list = databaseHandler.loadStocks();
        for(int k=0;k<list.size();k++){
            new StockDataDownloader(this).execute(list.get(k)[0],list.get(k)[1]);
        }
    }
    @Override
    protected void onDestroy() {
        databaseHandler.shutDown();
        super.onDestroy();
    }
    @Override
    protected void onResume(){
        super.onStart();


    }
}
