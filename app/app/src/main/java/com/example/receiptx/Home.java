package com.example.receiptx;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Home extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    PieChartView pieChartView;

    //holding category names
    ArrayList<String> categoryNames = new ArrayList<>();

    //top of home_old page items
    TextView greetingTextView;
    TextView dateTextView;
    TextView goalMessageTextView;
    TextView describeTextView;
    Button goalButton;
    ImageButton calenderButton;

    //categories text view
    TextView foodTextView;
    TextView retailTextView;
    TextView entertainmentTextView;
    TextView transportationTextView;
    TextView housingTextView;
    TextView miscTextView;

    //navigation
    TextView home;
    TextView receipt;
    TextView profile;
    TextView logout;

    //floating action buttons
    FloatingActionButton addFab;

    //floating action button text
    TextView imageCapTextView;
    TextView manuelEntTextView;

    //open menu boolean
    private boolean isOpen;

    //getting account that is signed in
    GoogleSignInAccount acct;

    Integer userID;

    //choosing the date
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    //GRAND TOTAL
    String grandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        addEmail();

        //initial setup of top part of home_old page
        greetingTextView = findViewById(R.id.greetingTextView);
        dateTextView = findViewById(R.id.dateTextView);
        goalMessageTextView = findViewById(R.id.goalMessageTextView);
        describeTextView = findViewById(R.id.describeTextView);
        calenderButton = findViewById(R.id.calenderButton);
        goalButton = findViewById(R.id.goalButton);

        //category text view setup
        foodTextView = findViewById(R.id.foodTextView);
        retailTextView = findViewById(R.id.retailTextView);
        entertainmentTextView = findViewById(R.id.entertainmentTextView);
        transportationTextView = findViewById(R.id.transportationTextView);
        housingTextView = findViewById(R.id.housingTextView);
        miscTextView = findViewById(R.id.miscTextView);

        //navigation menu setup
        home = findViewById(R.id.home);
        receipt = findViewById(R.id.receipt);
        profile = findViewById(R.id.profile);
        logout = findViewById(R.id.logout);

        //floating action bar
        addFab = findViewById(R.id.addFab);

        isOpen = false;

        //FAB textview
        imageCapTextView = findViewById(R.id.imageCapTextView);
        manuelEntTextView = findViewById(R.id.manualEntTextView);

        //testing pie chart api
        pieChartView = findViewById(R.id.chart);

        //updating text views
        greetingTextView.setText(getUserGreetingMessage());
        dateTextView.setText(setDateMessage());

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        grandTotal = getIntent().getStringExtra("grandTotal");
        updateCategories();
        updateGoalMessage();
    }

    //NAVIGATION

    //clicking on signout button
    public void onLogOutButtonClick(View view){

        //signing out
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Home.this, "Successfully signed out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Home.this, Login.class));
                        finish();
                    }
                });
    }

    //getting userID to work with and going to new activity
    public void goNewActivity(Class newActivity){
        final Intent intent = new Intent(Home.this, newActivity);

        String dbConnect = "http://cgi.soic.indiana.edu/~team43/receiptx/select_usersID.php";

        //getting access to link
        OkHttpClient client = new OkHttpClient();

        //giving the api paraments to work with
        RequestBody formBody = new FormBody.Builder()
                .add("email", getAccEmail())
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
                }else{
                    final String responseData = response.body().string();


                    //checking if we got data back
                    if (responseData.equalsIgnoreCase("0 results")){
                        //setting home screen to nothing
                    }
                    else{
                        try{
                            //converting the response to a JSONObject
                            final JSONObject responseJ = new JSONObject(responseData);
                            //getting the array out of the object of all receipts
                            final JSONArray responseA = responseJ.getJSONArray("usersID");

                            //getting user id
                            final String userID = responseA.getJSONObject(0).getString("usersID");

                            //going to new activity
                            Home.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    intent.putExtra("userID", userID);
                                    startActivity(intent);
                                }
                            });
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    //clicking on receipt button
    public void onReceiptClick(View view){
        goNewActivity(Receipt.class);
//        startActivity(new Intent(Home.this, Receipt.class));
    }

    //clicking on profile button
    public void onProfileButtonClick(View view){
        goNewActivity(Profile.class);
//        startActivity(new Intent(Home.this, Profile.class));
    }

    //clicking on floating action button
    public void onAddFabClick(View view){
        goNewActivity(AddReceiptName.class);
//        startActivity(new Intent(Home.this, AddReceiptName.class));

    }

    //CLICKING ON HOME SCREEN BUTTONS

    //clicking on goal button
    public void onGoalButtonClick(View view){

        goNewActivity(Goal.class);
    }

    //clicking on the calender button
    public void onCalenderButton(View view){
        //getting current date
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        //looking for the selected date
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                //adds one because starts at 0
                month += 1;

                //updating the text view
                String date = month + "/" + day + "/" + year;
                dateTextView.setText(date);
                System.out.println(date);

                updateCategories();
                updateGoalMessage();
            }
        };

        //setting up the dialog menu
        DatePickerDialog dialog = new DatePickerDialog(
                Home.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);

        //showing the date picking menu
        dialog.show();
    }

    //TOOLS TO BUILD HOME SCREEN

    //CRASHES ON THE PHONE - CHANING TO HELLO AND NAME - NOT ENOUGH SDK
    public String getUserGreetingMessage(){
        //adding hi and the name
        return "Hello, " + acct.getGivenName() + "!";
    }

    //getting the current date
    public String setDateMessage(){
        //getting the current date
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        //month starts at 0
        month += 1;

        //updating the text view
        String date = month + "/" + day + "/" + year;

        return date;
    }

    //getting the email thats logged in
    public String getAccEmail(){
        String personEmail = "";

        acct = GoogleSignIn.getLastSignedInAccount(Home.this);

        //checking if user has signed in
        if (acct != null) {
            personEmail = acct.getEmail();
        }

        return personEmail;
    }

    //setting categories and pie chart to 0 if no results there
    public void noCategoryResultsFound(){
        //current status
        String status = "Not Available";

        //updating text views
        foodTextView.setText(status);
        retailTextView.setText(status);
        entertainmentTextView.setText(status);
        transportationTextView.setText(status);
        housingTextView.setText(status);
        miscTextView.setText(status);

        //pie data
        List<SliceValue> pieData = new ArrayList<>();

        //nothing inside pie chart
        pieData.add(new SliceValue(0, Color.RED));
        pieData.add(new SliceValue(0, Color.BLUE));
        pieData.add(new SliceValue(0, Color.rgb(128, 0, 128)));
        pieData.add(new SliceValue(0, Color.GREEN));
        pieData.add(new SliceValue(0, Color.rgb(255, 165, 0)));
        pieData.add(new SliceValue(0, Color.GRAY));

        //creating the chart
        PieChartData pieChartData = new PieChartData(pieData);

        //text in the center of chart
        pieChartData.setHasCenterCircle(true)
                .setCenterText1(status)
                .setCenterText1FontSize(14)
                .setCenterText1Color(Color.parseColor("#0097A7"));

        //displaying the chart
        pieChartView.setPieChartData(pieChartData);

        describeTextView.setText("You don't have any information available for this month or year.");
    }

    //DATABASE CONNECTIONS HERE

    //INSERT QUERIES
    //adding email to the database
    public void addEmail() {
        String email = getAccEmail();

        String insertUserDB = "http://cgi.soic.indiana.edu/~team43/receiptx/insertUsers.php";

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .build();

        Request request = new Request.Builder()
                .url(insertUserDB)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
            }
        });
    }

    //SELECT QUERIES

    //ALGORITHM
    //PULL INFORMATION FROM DATABASE
    //CHECK IF DATE IS WITHIN THE CURRENT MONTH AND YEAR
    //YES - CHECK IF THERE IS A GOAL
    //      YES - HIDE BUTTON SHOW STATUS AMOUNT
    //      NO - SHOW BUTTON AND MESSAGE
    //NO - UPDATE THAT GOAL INFO AND HIDE THE BUTTONS - SHOW STATUS

    //upating the goal text views
    public void updateGoalMessage(){
        //date from the textview
        final String[] checkDate = dateTextView.getText().toString().split("/");
        String reformatDate = checkDate[2] + "-" + checkDate[0] + "-" + checkDate[1];

//        link to accesss
        String dbConnect = "http://cgi.sice.indiana.edu/~team43/receiptx/select_goalInfo.php";

        //getting access to link
        OkHttpClient client = new OkHttpClient();

        //giving the api paraments to work with
        RequestBody formBody = new FormBody.Builder()
                .add("email", getAccEmail())
                .add("date", reformatDate)
                .build();

        Request request = new Request.Builder()
                .url(dbConnect)
                .post(formBody)
                .build();

        //asking to get data
        client.newCall(request).enqueue(new Callback() {
            @Override
            //failed connection
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

                    //checking if not data was received
                    //no data
                    if(responseData.equalsIgnoreCase("0 results")){
                        //is the date within this year and month?
                        if(withinMonthYear(Integer.parseInt(checkDate[0]), Integer.parseInt(checkDate[2]))){
                            Home.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showGoalButton();
                                }
                            });
                        }
                        //not within the date
                        else{
                            Home.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    hideGoalMessageButton("N/A", "N/A");
                                }
                            });
                        }
                    }
                    //THERE IS DATA
                    else{
                        try{
                            //converting the response to a JSONObject
                            final JSONObject responseJ = new JSONObject(responseData);
                            //getting the array out of the object of all receipts
                            final JSONArray responseA = responseJ.getJSONArray("goalID");

                            //looking at current receipt
                            JSONObject currentGoal = responseA.getJSONObject(0);

                            //the amount
                            final String currentAmount = currentGoal.getString("setAmount");
                            //the status
                            final String currentStatus = currentGoal.getString("status");
                            //goal id to use later if needed
                            String currentGoalID = currentGoal.getString("goalID");


                            //checking if the month is not within the current month and year
                            if(withinMonthYear(Integer.parseInt(checkDate[0]), Integer.parseInt(checkDate[2])) == false && currentStatus.equalsIgnoreCase("ongoing")){
                                goToUpdateGoalStatus(currentGoalID, Double.parseDouble(currentAmount));
                            }else{
                                //updating the home screen
                                Home.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideGoalMessageButton("$" + currentAmount, currentStatus);
                                    }
                                });
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    //getting total amount user has spent
    public void goToUpdateGoalStatus(final String goalID, final Double setAmount){
        String dbConnect = "http://cgi.soic.indiana.edu/~team43/receiptx/select_all_total.php";

        //getting access to link
        OkHttpClient client = new OkHttpClient();

        //giving the api paraments to work with
        RequestBody formBody = new FormBody.Builder()
                .add("email", getAccEmail())
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
                }else{
                    final String responseData = response.body().string();

                    //checking if we got data back
                    if (responseData.equalsIgnoreCase("0 results")){

                    }
                    else{
                        try{
                            //converting the response to a JSONObject
                            final JSONObject responseJ = new JSONObject(responseData);
                            //getting the array out of the object
                            final JSONArray responseA = responseJ.getJSONArray("total");

                            //getting the data we want to use
                            JSONObject attempted = responseA.getJSONObject(0);

                            //getting the number of receipts entered
                            final String numTotal = attempted.getString("userTotal");

                            //updating the textview
                            Home.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Home.this, UpdateGoalStatus.class);
                                    //sending data over
                                    intent.putExtra("goalID", goalID);
                                    intent.putExtra("totalSpent", numTotal);
                                    intent.putExtra("setAmount", setAmount.toString());
                                    startActivity(intent);
                                }
                            });
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    //checking if its within the month
    public boolean withinMonthYear(Integer checkMonth, Integer checkYear){
        //getting the current date
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);


        //checking to see if its the next month in the same year
        if(month == checkMonth && year == checkYear){
            return true;
        }
        return false;
    }

    //hiding the button GOAL
    public void hideGoalMessageButton(String amount, String status){
        String goalMessage = "Goal Amount: " + amount;
        goalMessage += "\n\nGoal Status: " + status;

        //setting the goal message and hiding the button
        goalMessageTextView.setText(goalMessage);
        goalButton.setVisibility(View.INVISIBLE);
    }

    //showing the button
    public void showGoalButton(){
    //setting the goal message and hiding the button

    }



    //getting data from database and updating the category text views and pie chart
    //NEED TO UPDATE AND PASS VARIABLES TO IT
    public void updateCategories(){
        String teamCategoryDB = "http://cgi.soic.indiana.edu/~team43/receiptx/select_category_total.php";

        //getting month and year to be passed through to get data
        if (!dateTextView.getText().toString().equalsIgnoreCase("DATE GOES HERE")){
            //splitting up the date text
            String[] currentDateArray = dateTextView.getText().toString().split("/");

            //holding all the totals
            final List<String> totals = new ArrayList<>();
            //initial total values
            totals.add("0");
            totals.add("0");
            totals.add("0");
            totals.add("0");
            totals.add("0");
            totals.add("0");
            totals.add("0");

            //holding data for the pie chart
            final List<SliceValue> pieData = new ArrayList<>();

            //accessing internet
            OkHttpClient client = new OkHttpClient();

            //giving the api paraments to work with
            RequestBody formBody = new FormBody.Builder()
                    .add("email", getAccEmail())
                    .add("month", currentDateArray[0])
                    .add("year", currentDateArray[2])
                    .build();

            //getting to the link
            Request request = new Request.Builder().url(teamCategoryDB)
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
                    }else{
                        final String responseData = response.body().string();

                        //checking if we got data back
                        if (responseData.equalsIgnoreCase("0 results")){
                            //setting home screen to nothing
                            Home.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    noCategoryResultsFound();
                                }
                            });
                        }
                        else{
                            //updating the home screen categories
                            try{
                                //holding the total
                                Double grandTotal = 0.00;

                                //converting the response to a JSONObject
                                final JSONObject responseJ = new JSONObject(responseData);
                                //getting the array out of the object
                                final JSONArray responseA = responseJ.getJSONArray("categoryTotal");

                                //looping through the array
                                for(int i = 0; i < responseA.length(); i++) {
                                    JSONObject total = responseA.getJSONObject(i);

                                    String catName = total.getString("categoryName");
                                    String tot = total.getString("catTotal");

                                    //checkign to see what category total goes to what
                                    //updating the total values
                                    switch (catName) {
                                        case "Food":
                                            totals.set(1, tot);
                                            break;
                                        case "Retail":
                                            totals.set(4, tot);
                                            break;
                                        case "Entertainment":
                                            totals.set(0, tot);
                                            break;
                                        case "Transportation":
                                            totals.set(5, tot);
                                            break;
                                        case "Housing":
                                            totals.set(2, tot);
                                            break;
                                        case "Misc":
                                            totals.set(3, tot);
                                            break;
                                    }

                                    //need to check if we get a total for a category, should be 0 if nothing is there
                                    if (!tot.equalsIgnoreCase("null")) {
                                        grandTotal += Double.parseDouble(tot);
                                    }
                                }

                                //adding in the piedata from pieces we already know
                                pieData.add(new SliceValue(Float.parseFloat(totals.get(1)), Color.RED));
                                pieData.add(new SliceValue(Float.parseFloat(totals.get(4)), Color.BLUE));
                                pieData.add(new SliceValue(Float.parseFloat(totals.get(0)), Color.rgb(128, 0, 128)));
                                pieData.add(new SliceValue(Float.parseFloat(totals.get(5)), Color.GREEN));
                                pieData.add(new SliceValue(Float.parseFloat(totals.get(2)), Color.rgb(255, 165, 0)));
                                pieData.add(new SliceValue(Float.parseFloat(totals.get(3)), Color.GRAY));

                                //creating the chart
                                final PieChartData pieChartData = new PieChartData(pieData);

                                final Double allGrandTotal = Math.round(grandTotal * 100.0) / 100.0;

                                //text in the center of chart
                                pieChartData.setHasCenterCircle(true)
                                        .setCenterText1("Total:")
                                        .setCenterText2("$" + allGrandTotal)
                                        .setCenterText1FontSize(14)
                                        .setCenterText1Color(Color.parseColor("#0097A7"))
                                        .setCenterText2FontSize(14)
                                        .setCenterText2Color(Color.parseColor("#0097A7"));

                                Home.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //updating the textviews
                                        foodTextView.setText("$" + totals.get(1));
                                        retailTextView.setText("$" + totals.get(4));
                                        entertainmentTextView.setText("$" + totals.get(0));
                                        transportationTextView.setText("$" + totals.get(5));
                                        housingTextView.setText("$" + totals.get(2));
                                        miscTextView.setText("$" + totals.get(3));

                                        describeTextView.setText("The information you see below is for the selected month and year.");

                                        //displaying the chart
                                        pieChartView.setPieChartData(pieChartData);

                                        Intent intent = new Intent(Home.this, Home.class);
                                        intent.putExtra("grandTotal", allGrandTotal.toString());
                                    }
                                });
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
    }

}
