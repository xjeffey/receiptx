package com.example.receiptx;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

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

public class AddReceiptManualConfirm extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;

    //getting account that is signed in
    GoogleSignInAccount acct;

    //previous screen data
    String receiptName;
    String category;
    String storeName;
    String street;
    String city;
    String state;
    String zipCode;
    String date;
    ArrayList<Item> items;

    String userID;
    String catID;

    //initial total of receipt
    Double total = 0.0;

    //string of items
    String itemsText = "";

    //buttons
    Button cancelButton;
    Button dateButton;
    Button confirmButton;

    //edit text
    EditText receiptNameEditText;
    EditText storeNameEditText;
    EditText streetEditText;
    EditText cityEditText;
    EditText zipCodeEditText;
    EditText totalEditText;

    //dropdown menu
    Spinner categorySpinner;
    Spinner stateSpinner;

    //text view
    TextView dateTextView;
    TextView itemsTextView;

    //choosing the date
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receipt_manual_confirm);

        //updating user id
        userID = getIntent().getStringExtra("userID");
        System.out.println(userID);

        //setting up textviews
        dateTextView = findViewById(R.id.dateTextView);
        itemsTextView = findViewById(R.id.itemsTextView);

        //initializing edit text
        receiptNameEditText = findViewById(R.id.receiptNameEditText);
        storeNameEditText = findViewById(R.id.storeNameEditText);
        streetEditText = findViewById(R.id.streetEditText);
        cityEditText = findViewById(R.id.cityEditText);
        zipCodeEditText = findViewById(R.id.zipCodeEditText);
        totalEditText = findViewById(R.id.totalEditText);

        //TESTING DATA PASSING
        receiptName = getIntent().getStringExtra("receiptName");
        category = getIntent().getStringExtra("category");
        storeName = getIntent().getStringExtra("storeName");
        street = getIntent().getStringExtra("street");
        city = getIntent().getStringExtra("city");
        state = getIntent().getStringExtra("state");
        zipCode = getIntent().getStringExtra("zipCode");
        date = getIntent().getStringExtra("date");

        convertCategory(category);

        Bundle itemsBundle = getIntent().getExtras();
        items =(ArrayList<Item>) itemsBundle.getSerializable("items");

        //looking at each item
        for(Item item : items){
            //updating the total
            total += item.getItemPrice();

            //updating the items
            itemsText += "Item: " + item.getItemName() + "\tPrice:$ " + item.getItemPrice() + "\n";
        }

        //updating the edit text
        receiptNameEditText.setText(receiptName);
        storeNameEditText.setText(storeName);
        streetEditText.setText(street);
        cityEditText.setText(city);
        zipCodeEditText.setText(zipCode);
        totalEditText.setText(total.toString());

       //updating text views
        dateTextView.setText(date);
        itemsTextView.setText(itemsText);

        //setting up the drop down menu  (spinner) for category choices
        categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(AddReceiptManualConfirm.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categories));
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        //setting the selected category before
        categorySpinner.setSelection(categoryAdapter.getPosition(category));

        stateSpinner = findViewById(R.id.stateSpinner);
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(AddReceiptManualConfirm.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.states));
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        //setting the selected category before
        stateSpinner.setSelection(stateAdapter.getPosition(state));

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

        acct = GoogleSignIn.getLastSignedInAccount(AddReceiptManualConfirm.this);

        //checking if user has signed in
        if (acct != null) {
            personEmail = acct.getEmail();
        }

        return personEmail;
    }

    //button clicks

    //clicking on change date button
    public void changeDateButtonClick(View view){
//getting current date
        String[] dateInfo = dateTextView.getText().toString().split("-");
        int year = Integer.parseInt(dateInfo[0]);
        int month = Integer.parseInt(dateInfo[1]);
        int day = Integer.parseInt(dateInfo[2]);

        //setting up the dialog menu
        DatePickerDialog dialog = new DatePickerDialog(
                AddReceiptManualConfirm.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);

        //showing the date picking menu
        dialog.show();

        //looking for the selected date
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                //adds one because starts at 0
                month += 1;

                //updating the text view
                String selectedDate = year + "-" + month + "-" + day;
                dateTextView.setText(selectedDate);

                //update new date
                date = selectedDate;
            }
        };
    }

    //clicking on the confirm button
    public void confirmButtonClick(View view){
        if (receiptName.isEmpty() || category.equalsIgnoreCase("Select a Category") || storeName.isEmpty() ||
                street.isEmpty() || city.isEmpty() || state.equalsIgnoreCase("Select State") || zipCode.isEmpty() || zipCode.length() < 5 ||
                date.isEmpty() || totalEditText.getText().toString().isEmpty()) {
            //error message
            String errorMessage = "Please enter the following fields:\n";
            if(receiptName.isEmpty()){
                errorMessage += "\t- Receipt Name\n";
            }

            if(category.equalsIgnoreCase("Select a Category")){
                errorMessage += "\t- Category\n";
            }

            //adding certain errors
            if(storeName.isEmpty()){
                errorMessage += "\t- Store Name\n";
            }

            if(street.isEmpty()){
                errorMessage += "\t- Street Address\n";
            }
            if(city.isEmpty()){
                errorMessage += "\t- City\n";
            }
            if(state.equalsIgnoreCase("Select State")){
                errorMessage += "\t- State\n";
            }

            if(zipCode.isEmpty() || zipCode.length() < 5){
                errorMessage += "\t- Zip Code\n";
            }

            if(date.isEmpty()){
                errorMessage += "\t- Date\n";
            }

            if(totalEditText.getText().toString().isEmpty()){
                errorMessage += "\t- Total Amount\n";
            }

            //showing eror to user
            onErrorDialog(errorMessage);

        }else{
            //ADD DATA TO DATABASE
            insertManualReceipt();
        }

    }

    //clicking on the cancel button
    public void cancelButtonClick(View view){
        startActivity(new Intent(AddReceiptManualConfirm.this, Home.class));
    }

    //error message dialog
    //error dialog for missing fields
    public void onErrorDialog(String message){
        //creating error dialog
        androidx.appcompat.app.AlertDialog.Builder errorBuilder = new androidx.appcompat.app.AlertDialog.Builder(AddReceiptManualConfirm.this);
        //updating the message
        errorBuilder.setMessage(message);
        //updating the title
        errorBuilder.setTitle("Missing Field(s)");
        errorBuilder.setIcon(R.drawable.error);
        //creating the dialog
        androidx.appcompat.app.AlertDialog errorDialog = errorBuilder.create();
        //showing the dialog
        errorDialog.show();
    }

    //converting category name to id
    public void convertCategory(String catName){
        switch (catName){
            case "Food":
                catID = "2";
                break;
            case "Retail":
                catID = "5";
                break;
            case "Entertainment":
                catID = "1";
                break;
            case "Transportation":
                catID = "6";
                break;
            case "Housing":
                catID = "3";
                break;
            case "Misc":
                catID = "4";
                break;
        }
    }

    //inserting receipt
    public void insertManualReceipt(){
        String dbConnect = "http://cgi.soic.indiana.edu/~team43/receiptx/insertReceipt.php";

        //getting access to link
        OkHttpClient client = new OkHttpClient();

        //giving the api paraments to work with
        RequestBody formBody = new FormBody.Builder()
                .add("categoryID", catID)
                .add("usersID", userID)
                .add("title", receiptName)
                .add("receiptDate", date)
                .add("street", street)
                .add("city", city)
                .add("st", state)
                .add("zipCode", zipCode)
                .add("total", totalEditText.getText().toString())
                .add("vendor", storeName)
                .add("imagePath", "NULL")
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

                    //checking if we got data back
                    if (responseData.equalsIgnoreCase("0 results")){
                        //setting home screen to nothing
                    }else{
                        goAddItems();
                    }
                }
            }
        });
    }

    //inserting items
    public void goAddItems(){
        ////link to connect to database
        String dbConnect = "http://cgi.soic.indiana.edu/~team43/receiptx/select_receiptID.php";

        //getting access to link
        OkHttpClient client = new OkHttpClient();

        //giving the api paraments to work with
        RequestBody formBody = new FormBody.Builder()
                .add("email", getAccEmail())
                .add("title", receiptName)
                .add("categoryID", catID)
                .add("receiptDate", date)
                .add("total", totalEditText.getText().toString())
                .add("street", street)
                .add("city", city)
                .add("st", state)
                .add("zipCode", zipCode)
                .add("vendor", storeName)
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

                    //checking if we got data back
                    if (responseData.equalsIgnoreCase("0 results")){
                        //setting home screen to nothing
                    }else{
                        try{
                            //converting the response to a JSONObject
                            final JSONObject responseJ = new JSONObject(responseData);

                            //getting the array out of the object of all receipts
                            final JSONArray responseA = responseJ.getJSONArray("receiptID");

                            //looking at current receipt - only 1 retruned from DB
                            JSONObject currentReceipt = responseA.getJSONObject(0);

                            //getting receiptID
                            final String rID = currentReceipt.getString("receiptID");

                            AddReceiptManualConfirm.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(AddReceiptManualConfirm.this, InsertingItems.class);
                                    intent.putExtra("rID", rID);

                                    //testing to pass the array list
                                    Bundle itemsBundle = new Bundle();
                                    itemsBundle.putSerializable("items", items);

                                    //sending the array list
                                    intent.putExtras(itemsBundle);

                                    //going to new activity
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
}
