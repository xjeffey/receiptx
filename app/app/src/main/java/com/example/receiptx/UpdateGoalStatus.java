package com.example.receiptx;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateGoalStatus extends AppCompatActivity {
    //previous screen data
    String goalID;
    String totalSpent;
    String setAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_goal_status);

        goalID = getIntent().getStringExtra("goalID");
        totalSpent = getIntent().getStringExtra("totalSpent");
        setAmount = getIntent().getStringExtra("setAmount");

        Timer timer;

        //updating the goal
        updateGoalStatus(goalID, Double.parseDouble(setAmount), Double.parseDouble(totalSpent));

        //getting ready to move to new screen
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(UpdateGoalStatus.this, Home.class));
            }
        }, 5000);
    }

    //updating the goal status
    public void updateGoalStatus(String goalID, Double goalAmount, Double userTotal){
        String changeStatus = "Ongoing";

        //checking the status
        if(goalAmount < userTotal){
            changeStatus = "Failed";
        }
        else if(goalAmount >= userTotal){
            changeStatus = "Success";

        }
//        link to accesss
        String dbConnect = "http://cgi.sice.indiana.edu/~team43/receiptx/update_goal.php";

        //getting access to link
        OkHttpClient client = new OkHttpClient();

        //giving the api paraments to work with
        RequestBody formBody = new FormBody.Builder()
                .add("goalID", goalID)
                .add("setAmount", goalAmount.toString())
                .add("status", changeStatus)
                .build();

        Request request = new Request.Builder()
                .url(dbConnect)
                .post(formBody)
                .build();

        //asking to update
        client.newCall(request).enqueue(new Callback() {
            @Override
            //failed request
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            //getting data
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()){
                    throw new IOException("Unexpected code " + response);
                }else{
                    final String responseData = response.body().string();

                    //updated the goal
                    if (responseData.equalsIgnoreCase("record updated")){
                        System.out.println("GOAL HAS BEEN UPDATED");
                    }
                }
            }
        });
    }
}
