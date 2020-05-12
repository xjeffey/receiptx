package com.example.receiptx;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InsertingItems extends AppCompatActivity {
    //item data
    String rID;
    ArrayList<Item> items;

    //timers to go to new screen
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserting_items);

        //getting the data
        Bundle itemsBundle = getIntent().getExtras();
        items =(ArrayList<Item>) itemsBundle.getSerializable("items");
        rID = getIntent().getStringExtra("rID");

        //inserting the receipt
        for(Item item : items){
            System.out.println(item.getItemName());
            insertItem(item);
        }

        //getting ready to move to new screen
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(InsertingItems.this, Receipt.class));
            }
        }, 5000);
    }

    //inserting item
    public void insertItem(Item toAddItem){
        String dbConnect = "http://cgi.soic.indiana.edu/~team43/receiptx/insertItems.php";

        //getting access to link
        OkHttpClient client = new OkHttpClient();

        //giving the api paraments to work with
        RequestBody formBody = new FormBody.Builder()
                .add("receiptID", rID)
                .add("item", toAddItem.getItemName())
                .add("price", toAddItem.getItemPrice().toString())
                .build();

        Request request = new Request.Builder()
                .url(dbConnect)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    throw new IOException("Unexpected code " + response);
                }else {
                    final String responseData = response.body().string();
                    System.out.println(responseData);
                }
            }
        });
    }
}
