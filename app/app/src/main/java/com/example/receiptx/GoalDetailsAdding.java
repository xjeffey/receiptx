package com.example.receiptx;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GoalDetailsAdding extends AppCompatActivity {
    //goal id
    String goalID;
    //user id
    String userID;

    //timers to go to new screen
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_details_adding);

        //upadting the goal id and user id
        goalID = getIntent().getStringExtra("goalID");
        userID = getIntent().getStringExtra("userID");

        //inserting goal details here
        insertGoalDetail(goalID, userID, getFirstDayOfMonth(), getLastDayOfMonth());

        //getting ready to move to new screen
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(GoalDetailsAdding.this, Home.class));
            }
        }, 5000);
    }


    //inserting the goal detail for a goal
    public void insertGoalDetail(String goalID, String userID, String startDate, String endDate){
//        link to accesss
        String dbConnect = "http://cgi.sice.indiana.edu/~team43/receiptx/insertGoaldetails.php";

        //getting access to link
        OkHttpClient client = new OkHttpClient();

        //giving the api paraments to work with
        RequestBody formBody = new FormBody.Builder()
                .add("goalID", goalID)
                .add("usersID", userID)
                .add("dateStart", startDate)
                .add("dateEnd", endDate)
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

    //getting first day of a month
    public String getFirstDayOfMonth(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = 1;

        return year + "-" + month + "-" + day;
    }

    //getting last day of a month
    public String getLastDayOfMonth(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = 0;

        //checkign for last day in that month
        //1, 3, 5,7,8,10,12 - 31 days
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
            day = 31;
        }
        //4,6,9,11 - 30 days
        if(month == 4 || month == 6 || month == 9 || month == 11){
            day = 30;
        }

        //2 - 28 days
        if(month == 2){
            day = 28;
        }

        return year + "-" + month + "-" + day;
    }
}
