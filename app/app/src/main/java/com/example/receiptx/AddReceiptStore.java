package com.example.receiptx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddReceiptStore extends AppCompatActivity {
    Button nextButton;
    Button cancelButton;

    EditText storeNameEditText;
    EditText streetEditText;
    EditText cityEditText;
    EditText zipEditText;

    Spinner stateSpinner;

    //previous screen data
    String receiptName;
    String category;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receipt_store);

        userID = getIntent().getStringExtra("userID");
        System.out.println(userID);

        //INITIAL SET UP
        nextButton = findViewById(R.id.nextButton);
        cancelButton = findViewById(R.id.cancelButton);

        storeNameEditText = findViewById(R.id.receiptNameEditText);
        streetEditText = findViewById(R.id.streetNameEditText);
        cityEditText = findViewById(R.id.cityEditText);
        zipEditText = findViewById(R.id.zipEditText);

        stateSpinner = findViewById(R.id.stateSpinner);
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(AddReceiptStore.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.states));
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        //setting the selected category before
        stateSpinner.setSelection(stateAdapter.getPosition(getIntent().getStringExtra("state")));

        //TESTING DATA PASSING
        receiptName = getIntent().getStringExtra("receiptName");
        category = getIntent().getStringExtra("category");
        System.out.println("Data received");
        System.out.println(receiptName);
        System.out.println(category);
    }

    //clicking on cancel button
    public void onCancelButtonClick(View view){
        startActivity(new Intent(AddReceiptStore.this, Home.class));
    }

    //clicking on next button
    public void onNextButtonClick(View view){
        //data to pass
        String storeName = storeNameEditText.getText().toString();
        String street = streetEditText.getText().toString();
        String city = cityEditText.getText().toString();
        String state = stateSpinner.getSelectedItem().toString();
        String zipCode = zipEditText.getText().toString();



        //checking for empty fields
        if(storeName.isEmpty() || street.isEmpty() || city.isEmpty() || state.equalsIgnoreCase("Select State") ||
                zipCode.isEmpty() || zipCode.length() < 5){

            //error message
            String errorMessage = "Please enter the following fields:\n";

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

            //displaying error dialog
            onErrorDialog(errorMessage);
        }else{
            //passing information
            Intent intent = new Intent(AddReceiptStore.this, AddReceiptDate.class);
            intent.putExtra("receiptName", receiptName);
            intent.putExtra("category", category);
            intent.putExtra("storeName", storeName);
            intent.putExtra("street", street);
            intent.putExtra("city", city);
            intent.putExtra("state", state);
            intent.putExtra("zipCode", zipCode);
            intent.putExtra("userID", userID);
            startActivity(intent);
        }
    }

    //error dialog for missing fields
    public void onErrorDialog(String message){
        //creating error dialog
        AlertDialog.Builder errorBuilder = new AlertDialog.Builder(AddReceiptStore.this);
        //updating the message
        errorBuilder.setMessage(message);
        //updating the title
        errorBuilder.setTitle("Missing Field(s)");
        errorBuilder.setIcon(R.drawable.error);
        //creating the dialog
        AlertDialog errorDialog = errorBuilder.create();
        //showing the dialog
        errorDialog.show();
    }
}
