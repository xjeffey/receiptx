package com.example.receiptx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Profile extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;

    //navigation
    TextView home;
    TextView receipt;
    TextView profile;
    TextView logout;

    //floating action buttons
    FloatingActionButton addFab;

    //textviews to update
    TextView receiptsEnteredTextView;
    TextView totalSpentTextView;
    TextView goalsAttemptTextView;
    TextView goalsCompletedTextView;
    TextView goalAmountTextView;
    TextView totalSavedTextView;
    TextView userTextView;
    TextView savedTextView;

    //getting account that is signed in
    GoogleSignInAccount acct;

    //user id
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        //updating user id
        userID = getIntent().getStringExtra("userID");
        System.out.println("USER ID" + userID);

        //initial button setup
        //navigation menu setup
        home = findViewById(R.id.home);
        receipt = findViewById(R.id.receipt);
        profile = findViewById(R.id.profile);
        logout = findViewById(R.id.logout);

        //floating action bar
        addFab = findViewById(R.id.addFab);


//        initialize textviews to show data
        receiptsEnteredTextView = findViewById(R.id.receiptsEnteredTextView);
        totalSpentTextView = findViewById(R.id.totalSpentTextView);
        goalsAttemptTextView = findViewById(R.id.goalsAttemptTextView);
        goalsCompletedTextView = findViewById(R.id.goalsCompletedTextView);
        goalAmountTextView = findViewById(R.id.goalAmountTextView);
        totalSavedTextView = findViewById(R.id.totalSavedTextView);
        userTextView = findViewById(R.id.userTextView);
        savedTextView = findViewById(R.id.savedTextView);

        //updating the textviews
        updateReceiptsEntered();
        updateTotalSpent();
        updateGoalsAttempted();
        updateGoalsCompleted();
        updateTotalGoals();
        updateTotalSaved();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        userTextView.setText("Hello, " + acct.getGivenName());
    }

    //getting the email thats logged in
    public String getAccEmail(){
        String personEmail = "";

        acct = GoogleSignIn.getLastSignedInAccount(Profile.this);

        //checking if user has signed in
        if (acct != null) {
            personEmail = acct.getEmail();
        }

        return personEmail;
    }

    //getting data about user

    //getting number of receipts user entered
    public void updateReceiptsEntered(){
//        link to accesss
        String dbConnect = "http://cgi.soic.indiana.edu/~team43/receiptx/select_receipt_count.php";

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
                    System.out.println(responseData);

                    //checking if we got data back
                    if (responseData.equalsIgnoreCase("0 results")){
                        //setting home screen to nothing
                        Profile.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                receiptsEnteredTextView.setText("0");
                            }
                        });
                    }
                    else{
                        try{
                            //converting the response to a JSONObject
                            final JSONObject responseJ = new JSONObject(responseData);
                            //getting the array out of the object
                            final JSONArray responseA = responseJ.getJSONArray("count");

                            //getting the data we want to use
                            JSONObject attempted = responseA.getJSONObject(0);

                            //getting the number of receipts entered
                            final String numEntered = attempted.getString("receiptsEntered");

                            //updating the textview
                            Profile.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    receiptsEnteredTextView.setText(numEntered);
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

    //getting total amount user has spent
    public void updateTotalSpent(){
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
                    System.out.println(responseData);

                    //checking if we got data back
                    if (responseData.equalsIgnoreCase("0 results")){
                        //setting home screen to nothing
                        Profile.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                totalSpentTextView.setText("$0.00");
                            }
                        });
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
                            Profile.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    totalSpentTextView.setText("$" + numTotal);
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

    //getting number of goals attempted
    public void updateGoalsAttempted(){
        String dbConnect = "http://cgi.soic.indiana.edu/~team43/receiptx/select_goals_attempt.php";

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
                    System.out.println(responseData);

                    //checking if we got data back
                    if (responseData.equalsIgnoreCase("0 results")){
                        //setting home screen to nothing
                        Profile.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                goalsAttemptTextView.setText("0");
                            }
                        });
                    }
                    else{
                        try{
                            //converting the response to a JSONObject
                            final JSONObject responseJ = new JSONObject(responseData);
                            //getting the array out of the object
                            final JSONArray responseA = responseJ.getJSONArray("attempt");

                            //getting the data we want to use
                            JSONObject attempted = responseA.getJSONObject(0);

                            //getting the number of receipts entered
                            final String numAttempted = attempted.getString("goalsEntered");

                            //updating the textview
                            Profile.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    goalsAttemptTextView.setText(numAttempted);
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

    //getting number of goals completed
    public void updateGoalsCompleted(){
        String dbConnect = "http://cgi.soic.indiana.edu/~team43/receiptx/select_goals_completed.php";

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
                    System.out.println(responseData);

                    //checking if we got data back
                    if (responseData.equalsIgnoreCase("0 results")){
                        //setting home screen to nothing
                        Profile.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                goalsCompletedTextView.setText("0");
                            }
                        });
                    }
                    else{
                        try{
                            //converting the response to a JSONObject
                            final JSONObject responseJ = new JSONObject(responseData);
                            //getting the array out of the object
                            final JSONArray responseA = responseJ.getJSONArray("completed");

                            //getting the data we want to use
                            JSONObject attempted = responseA.getJSONObject(0);

                            //getting the number of receipts entered
                            final String numCompleted = attempted.getString("goalsCompleted");

                            //updating the textview
                            Profile.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    goalsCompletedTextView.setText(numCompleted);
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

    //getting total of goals completed
    public void updateTotalGoals(){
        String dbConnect = "http://cgi.soic.indiana.edu/~team43/receiptx/select_goals_total.php";

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
                    System.out.println(responseData);

                    //checking if we got data back
                    if (responseData.equalsIgnoreCase("0 results")){
                        //setting home screen to nothing
                        Profile.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                goalAmountTextView.setText("$0.00");
                            }
                        });
                    }
                    else{
                        try{
                            //converting the response to a JSONObject
                            final JSONObject responseJ = new JSONObject(responseData);
                            //getting the array out of the object
                            final JSONArray responseA = responseJ.getJSONArray("completedTotal");

                            //getting the data we want to use
                            JSONObject attempted = responseA.getJSONObject(0);

                            //getting the number of receipts entered
                            String numTotal = attempted.getString("goalsCompletedTotal");

                            if(numTotal.equalsIgnoreCase("null")){
                                numTotal = "0";
                            }

                            final String toAddTotal = numTotal;
                            //updating the textview
                            Profile.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    goalAmountTextView.setText("$" + toAddTotal);
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

    //getting total of money saved (spent - goals)
    public void updateTotalSaved(){
//        CRASHING BECAUSE THIS RUNS FIRST BEFORE THE DATABASE CONNECTIONS
        //getting data from goal amount
        findGoalTotal();
    }

    public void calcTotalSaved(final Double goalTotal){
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
                    Double totalSpent = 0.0;

                    //checking if we got data back
                    if (responseData.equalsIgnoreCase("0 results")){
                        //setting home screen to nothing

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
                            String numTotal = attempted.getString("userTotal");

                            totalSpent = Double.parseDouble(numTotal);

                            final Double totalSaved = goalTotal - totalSpent;
                            //updating the textview
                            Profile.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //determining if user loss or saved money
                                    if (totalSaved < 0){
                                        totalSavedTextView.setText("$0");
                                    }else{
                                        totalSavedTextView.setText("$" + Math.abs(totalSaved));
                                    }

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

    //getting total of goals completed
    public void findGoalTotal(){
        String dbConnect = "http://cgi.soic.indiana.edu/~team43/receiptx/select_goals_total.php";

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
                    Double goalAmount = 0.0;

                    //checking if we got data back
                    if (responseData.equalsIgnoreCase("0 results")){
                        //setting home screen to nothing
                        goalAmount = 0.0;
                        calcTotalSaved(goalAmount);
                    }
                    else{
                        try{
                            //converting the response to a JSONObject
                            final JSONObject responseJ = new JSONObject(responseData);
                            //getting the array out of the object
                            final JSONArray responseA = responseJ.getJSONArray("completedTotal");

                            //getting the data we want to use
                            JSONObject attempted = responseA.getJSONObject(0);

                            //getting the number of receipts entered
                            String numTotal = attempted.getString("goalsCompletedTotal");

                            if(!numTotal.equalsIgnoreCase("null")){
                                goalAmount = Double.parseDouble(numTotal);
                            }

                            calcTotalSaved(goalAmount);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    //NAVIGATION

    //going to new activity and sending user id
    public void goActivity(Class activity){
        Intent intent = new Intent(Profile.this, activity);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    //clicking on signout button
    public void onLogOutButtonClick(View view){

        //signing out
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Profile.this, "Successfully signed out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Profile.this, Login.class));
                        finish();
                    }
                });
    }

    //clicking on receipt button
    public void onReceiptClick(View view){
        goActivity(Receipt.class);
//        startActivity(new Intent(Profile.this, Receipt.class));
    }

    //clicking on profile button
    public void onHomeButtonClick(View view){
        goActivity(Home.class);
//        startActivity(new Intent(Profile.this, Home.class));
    }

    //clicking on floating action button
    public void onAddFabClick(View view){
        goActivity(AddReceiptName.class);
//        startActivity(new Intent(Profile.this, AddReceiptName.class));
    }
}
