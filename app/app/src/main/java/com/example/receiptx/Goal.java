package com.example.receiptx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

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

public class Goal extends AppCompatActivity {
    //getting the user ID
    String userID;

    //textview
    EditText amountEditText;

    Button setGoalButton;
    Button cancelButton;

    //getting account that is signed in
    GoogleSignInAccount acct;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        //updating the user id
        userID = getIntent().getStringExtra("userID");

        amountEditText = findViewById(R.id.amountEditText);

        //button set up
        setGoalButton = findViewById(R.id.setGoalButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    //inserting the goal
    public void insertGoal(final String id, final Double goalAmount, final String status){
//        link to accesss
        String dbConnect = "http://cgi.sice.indiana.edu/~team43/receiptx/insertGoal.php";

        //getting access to link
        OkHttpClient client = new OkHttpClient();

        //giving the api paraments to work with
        RequestBody formBody = new FormBody.Builder()
                .add("usersID", id)
                .add("setAmount", goalAmount.toString())
                .add("status", status)
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
                    //checking if we got data back
                    if (responseData.equalsIgnoreCase("0 results")) {
                        //setting home screen to nothing
                    } else {
                        goToAddGoalDetails(getAccEmail(), goalAmount.toString(), status);
                    }
                }
            }
        });
    }

    //inserting goal details
    public void goToAddGoalDetails(String email, String setAmount, String status){
//        link to accesss
        String dbConnect = "http://cgi.sice.indiana.edu/~team43/receiptx/select_goalID.php";

        //getting access to link
        OkHttpClient client = new OkHttpClient();

        //giving the api paraments to work with
        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("setAmount", setAmount)
                .add("status", status)
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

                    //checking if we got data back
                    if (responseData.equalsIgnoreCase("0 results")) {
                        //setting home screen to nothing
                    }else{
                        try{
                            //converting the response to a JSONObject
                            final JSONObject responseJ = new JSONObject(responseData);
                            //getting the array out of the object of all receipts
                            final JSONArray responseA = responseJ.getJSONArray("goalID");

                            //getting inserting goal recently
                            JSONObject currentGoal = responseA.getJSONObject(0);
                            final String goalID = currentGoal.getString("goalID");

                            //going to new screen
                            Goal.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //sending to goaldetails to add data
                                Intent intent = new Intent(Goal.this, GoalDetailsAdding.class);
                                intent.putExtra("goalID", goalID);
                                intent.putExtra("userID", userID);
                                startActivity(intent);
                            }
                        });
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
    }

    //getting the email thats logged in
    public String getAccEmail(){
        String personEmail = "";

        acct = GoogleSignIn.getLastSignedInAccount(Goal.this);

        //checking if user has signed in
        if (acct != null) {
            personEmail = acct.getEmail();
        }

        return personEmail;
    }

    //checking if goal has been added
    //error dialog for missing fields
    public void onErrorDialog(){
        //creating error dialog
        androidx.appcompat.app.AlertDialog.Builder errorBuilder = new androidx.appcompat.app.AlertDialog.Builder(Goal.this);
        //updating the message
        errorBuilder.setMessage("Please add a goal amount!");
        //updating the title
        errorBuilder.setTitle("Missing Field(s)");
        errorBuilder.setIcon(R.drawable.error);
        //creating the dialog
        androidx.appcompat.app.AlertDialog errorDialog = errorBuilder.create();
        //showing the dialog
        errorDialog.show();
    }

    //clickign on set goal
    public void onSetGoalButtonClick(View view){
        //seeing if something was entered
        if(amountEditText.getText().toString().isEmpty()){
            onErrorDialog();
        }
        else{
            Double goalAmount = Double.parseDouble(amountEditText.getText().toString());
            insertGoal(userID, goalAmount, "ongoing");
        }

    }

    //clickgin on cancel button
    public void onCancelButtonClick(View view){
        startActivity(new Intent(Goal.this, Home.class));
    }
}
