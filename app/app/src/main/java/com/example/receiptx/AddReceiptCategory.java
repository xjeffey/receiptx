package com.example.receiptx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddReceiptCategory extends AppCompatActivity {
    Button nextButton;
    Button cancelButton;

    Spinner categorySpinner;

    //previous screen data
    String receiptName;

    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receipt_category);

        userID = getIntent().getStringExtra("userID");
        System.out.println(userID);

        //TESTING DATA PASSING
        receiptName  = getIntent().getStringExtra("receiptName");
        System.out.println("Data received");
        System.out.println(receiptName);

        //INITIAL SET UP
        nextButton = findViewById(R.id.nextButton);
        cancelButton = findViewById(R.id.cancelButton);

        //setting up the drop down menu  (spinner) for category choices
        categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(AddReceiptCategory.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.categories));
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        //setting the selected category before
        categorySpinner.setSelection(categoryAdapter.getPosition(getIntent().getStringExtra("category")));


    }

    //clicking on cancel button
    public void onCancelButtonClick(View view){
        startActivity(new Intent(AddReceiptCategory.this, Home.class));
    }

    //clicking on next button
    public void onNextButtonClick(View view){
        //passing information data
        String category = categorySpinner.getSelectedItem().toString();

        if (category.equalsIgnoreCase("Select a Category")){
            onErrorDialog();
        }
        else{
            //passing information
            Intent intent = new Intent(AddReceiptCategory.this, AddReceiptStore.class);
            intent.putExtra("receiptName", receiptName);
            intent.putExtra("category", category);
            intent.putExtra("userID", userID);
            startActivity(intent);
        }
    }

    //error dialog for missing fields
    public void onErrorDialog(){
        //creating error dialog
        AlertDialog.Builder errorBuilder = new AlertDialog.Builder(AddReceiptCategory.this);
        //updating the message
        errorBuilder.setMessage("Please select a category!");
        //updating the title
        errorBuilder.setTitle("Missing Field(s)");
        errorBuilder.setIcon(R.drawable.error);
        //creating the dialog
        AlertDialog errorDialog = errorBuilder.create();
        //showing the dialog
        errorDialog.show();
    }
}
