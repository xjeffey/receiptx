package com.example.receiptx;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Receipt extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;

    //user id
    String userID;

    //navigation
    TextView home;
    TextView receipt;
    TextView profile;
    TextView logout;
    TextView sortTextView;

    //edit text
    EditText searchEditText;

    //buttons
    Button sortButton;
    ImageButton searchButton;

    //floating action buttons
    FloatingActionButton addFab;

    //open menu boolean
    private boolean isOpen;

    //getting account that is signed in
    GoogleSignInAccount acct;

    //List View
    ListView receiptListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt);

        //updating user id
        userID = getIntent().getStringExtra("userID");

        //initial button setup
        //navigation menu setup
        home = findViewById(R.id.home);
        receipt = findViewById(R.id.receipt);
        profile = findViewById(R.id.profile);
        logout = findViewById(R.id.logout);
        sortTextView = findViewById(R.id.sortTextView);

        searchEditText = findViewById(R.id.searchEditText);

        //floating action bar
        addFab = findViewById(R.id.addFab);

        //listview
        receiptListView = findViewById(R.id.itemEntryListView);

        //buttons
        searchButton = findViewById(R.id.searchImageButton);
        sortButton = findViewById(R.id.sortButton);

        //updating list of receipts
        updateReceiptsEntered("http://cgi.sice.indiana.edu/~team43/receiptx/select_all_receipt.php");

        isOpen = false;

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    //getting the email thats logged in
    public String getAccEmail(){
        String personEmail = "";

        acct = GoogleSignIn.getLastSignedInAccount(Receipt.this);

        //checking if user has signed in
        if (acct != null) {
            personEmail = acct.getEmail();
        }

        return personEmail;
    }

    //updating the receipts
    //getting number of receipts user entered
    public void updateReceiptsEntered(String dbLinkConnector){
//        link to accesss
//        String dbConnect = "http://cgi.sice.indiana.edu/~zhenga/receiptx/select_all_receipt.php";

        //getting access to link
        OkHttpClient client = new OkHttpClient();

        //giving the api paraments to work with
        RequestBody formBody = new FormBody.Builder()
                .add("email", getAccEmail())
                .build();

        Request request = new Request.Builder()
                .url(dbLinkConnector)
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
                            //holding all the receipts
                            ArrayList<ReceiptSummary> receiptSummaries = new ArrayList<>();

                            //converting the response to a JSONObject
                            final JSONObject responseJ = new JSONObject(responseData);
                            //getting the array out of the object of all receipts
                            final JSONArray responseA = responseJ.getJSONArray("allReceipt");

                            //looping through each receipt
                            for(int i = 0; i < responseA.length(); i++){
                                //looking at current receipt
                                JSONObject currentReceipt = responseA.getJSONObject(i);

                                //extracting summary information
                                Integer rID = Integer.parseInt(currentReceipt.getString("receiptID"));
                                String rName = currentReceipt.getString("title");
                                String rStore = currentReceipt.getString("vendor");
                                String rDate = currentReceipt.getString("receiptDate");
                                String rCategory = currentReceipt.getString("categoryName");
                                String rAddress = currentReceipt.getString("street") + " " + currentReceipt.getString("city") + ", " + currentReceipt.getString("st") + " " + currentReceipt.getString("zipCode");
                                Double rTotal = Double.parseDouble(currentReceipt.getString("total"));
                                String rImagePath = currentReceipt.getString("imagePath");

                                //creating objects
                                ReceiptSummary receiptSummary = new ReceiptSummary(rID, rName, rStore, rDate, rCategory, rAddress, rTotal, rImagePath);

                                //adding to array list
                                receiptSummaries.add(receiptSummary);
                            }

                            final ArrayList<ReceiptSummary> receiptSummariesFinal = receiptSummaries;

                            //showing on receipts
                            Receipt.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ReceiptSummaryListAdapter adapter = new ReceiptSummaryListAdapter(Receipt.this, R.layout.adapter_layout, receiptSummariesFinal);
                                    receiptListView.setAdapter(adapter);
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

    //searching for receipt by user input
    public void searchByInput(){
        //getting access to link
        OkHttpClient client = new OkHttpClient();

        //giving the api paraments to work with
        RequestBody formBody = new FormBody.Builder()
                .add("email", getAccEmail())
                .add("input", searchEditText.getText().toString().toLowerCase())
                .build();

        Request request = new Request.Builder()
                .url("http://cgi.sice.indiana.edu/~team43/receiptx/search.php")
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
                        System.out.println("NO RESULTS");
                    }
                    else{
                        try{
                            //holding all the receipts
                            ArrayList<ReceiptSummary> receiptSummaries = new ArrayList<>();

                            //converting the response to a JSONObject
                            final JSONObject responseJ = new JSONObject(responseData);
                            //getting the array out of the object of all receipts
                            final JSONArray responseA = responseJ.getJSONArray("allReceipt");

                            //looping through each receipt
                            for(int i = 0; i < responseA.length(); i++){
                                //looking at current receipt
                                JSONObject currentReceipt = responseA.getJSONObject(i);

                                //extracting summary information
                                Integer rID = Integer.parseInt(currentReceipt.getString("receiptID"));
                                String rName = currentReceipt.getString("title");
                                String rStore = currentReceipt.getString("vendor");
                                String rDate = currentReceipt.getString("receiptDate");
                                String rCategory = currentReceipt.getString("categoryName");
                                String rAddress = currentReceipt.getString("street") + " " + currentReceipt.getString("city") + ", " + currentReceipt.getString("st") + " " + currentReceipt.getString("zipCode");
                                Double rTotal = Double.parseDouble(currentReceipt.getString("total"));
                                String rImagePath = currentReceipt.getString("imagePath");

                                //creating objects
                                ReceiptSummary receiptSummary = new ReceiptSummary(rID, rName, rStore, rDate, rCategory, rAddress, rTotal, rImagePath);

                                //adding to array list
                                receiptSummaries.add(receiptSummary);
                            }

                            final ArrayList<ReceiptSummary> receiptSummariesFinal = receiptSummaries;

                            //showing on receipts
                            Receipt.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ReceiptSummaryListAdapter adapter = new ReceiptSummaryListAdapter(Receipt.this, R.layout.adapter_layout, receiptSummariesFinal);
                                    receiptListView.setAdapter(adapter);
                                    sortTextView.setText("Status: User Input");
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

    //clicking on the sort button
    public void onSortButtonClick(View view){
        //showing alert to allow user to select a sort
        AlertDialog.Builder sortBuilder = new AlertDialog.Builder(Receipt.this);
        //creating the custom alert dialog
        View sortView = getLayoutInflater().inflate(R.layout.filter_dialog_spinner, null);
        //setting title of the dialog
        sortBuilder.setTitle("Select a sort:");
        //defining the spinner
        final Spinner sortSpinner = (Spinner) sortView.findViewById(R.id.filterSpinner);

        //setting up the drop down menu  (spinner) for category choices
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(Receipt.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.filters));
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortAdapter);

        //the positive and negative buttons
        //clicking OK
        sortBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedSort = sortSpinner.getSelectedItem().toString();
                String dbLink = "";

                //determining which filter to use
                switch (selectedSort){
                    case "Default":
                        dbLink = "http://cgi.sice.indiana.edu/~team43/receiptx/select_all_receipt.php";
                        break;
                    case "Date: Recent to Oldest":
                        dbLink = "http://cgi.sice.indiana.edu/~team43/receiptx/date_recent_latest.php";
                        break;
                    case "Date: Oldest to Recent":
                        dbLink = "http://cgi.sice.indiana.edu/~team43/receiptx/date_latest_recent.php";
                        break;
                    case "Receipt Title: A-Z":
                        dbLink = "http://cgi.sice.indiana.edu/~team43/receiptx/receiptName_A_Z.php";
                        break;
                    case "Receipt Title: Z-A":
                        dbLink = "http://cgi.sice.indiana.edu/~team43/receiptx/receiptName_Z_A.php";
                        break;
                    case "Total: High to Low":
                        dbLink = "http://cgi.sice.indiana.edu/~team43/receiptx/total_high_low.php";
                        break;
                    case "Total: Low to High":
                        dbLink = "http://cgi.sice.indiana.edu/~team43/receiptx/total_low_high.php";
                        break;
                    case "Vendor: A-Z":
                        dbLink = "http://cgi.sice.indiana.edu/~team43/receiptx/vendor_A_Z.php";
                        break;
                    case "Vendor: Z-A":
                        dbLink = "http://cgi.sice.indiana.edu/~team43/receiptx/vendor_Z_A.php";
                        break;
                }
                //UPDATING THE RECEIPTS SHOWN
                sortTextView.setText("Sort: " + selectedSort);
                updateReceiptsEntered(dbLink);
            }
        });

        //pressing on CANCEL
        sortBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //showing the alert
        sortBuilder.setView(sortView);
        AlertDialog sortDialog = sortBuilder.create();
        sortDialog.show();
    }

    //clickign on search filter
    public void onSearchButtonClick(View view){
        if (!searchEditText.getText().toString().isEmpty()){
            searchByInput();
            System.out.println("NOT EMPTY");
        }
        else{
            System.out.println("EMPTY");
        }
    }
    //NAVIGATION

    //clicking on signout button
    public void onLogOutButtonClick(View view){

        //signing out
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Receipt.this, "Successfully signed out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Receipt.this, Login.class));
                        finish();
                    }
                });
    }

    //going to new activity and sending user id
    public void goActivity(Class activity){
        Intent intent = new Intent(Receipt.this, activity);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    //clicking on receipt button
    public void onProfileClick(View view){
        goActivity(Profile.class);
//        startActivity(new Intent(Receipt.this, Profile.class));
    }

    //clicking on profile button
    public void onHomeButtonClick(View view){
        goActivity(Home.class);
//        startActivity(new Intent(Receipt.this, Home.class));
    }

    //clicking on floating action button
    public void onAddFabClick(View view){
        goActivity(AddReceiptName.class);
//        startActivity(new Intent(Receipt.this, AddReceiptName.class));
    }
}
