package com.example.receiptx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddReceiptItems extends AppCompatActivity {

    //previous screen data
    String receiptName;
    String category;
    String storeName;
    String street;
    String city;
    String state;
    String zipCode;
    String date;

    String userID;

    //buttons
    Button cancelButton;
    Button nextButton;
    Button addButton;

    //edit text
    EditText itemNameEditText;
    EditText itemPriceEditText;

    //list view
    ListView entryListView;

    //holding all the items
    ArrayList<Item> items = new ArrayList<>();

    //updating the list view variable
    ItemListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receipt_items);

        //updating user id
        userID = getIntent().getStringExtra("userID");
        System.out.println(userID);

        //initial set up
        cancelButton = findViewById(R.id.cancelButton);
        nextButton = findViewById(R.id.nextButton);
        addButton = findViewById(R.id.addButton);

        entryListView = findViewById(R.id.entryListView);

        //setting up
        itemNameEditText = findViewById(R.id.itemNameEditText);
        itemPriceEditText = findViewById(R.id.itemPriceEditText);

        //initializing the array list
        items = new ArrayList<Item>();

        //updating the adapter with items array list
        adapter = new ItemListAdapter(AddReceiptItems.this, R.layout.items_adapter_layout, items);

        //updating the list view of items
        entryListView.setAdapter(adapter);

        //TESTING DATA PASSING
        receiptName = getIntent().getStringExtra("receiptName");
        category = getIntent().getStringExtra("category");
        storeName = getIntent().getStringExtra("storeName");
        street = getIntent().getStringExtra("street");
        city = getIntent().getStringExtra("city");
        state = getIntent().getStringExtra("state");
        zipCode = getIntent().getStringExtra("zipCode");
        date = getIntent().getStringExtra("date");
    }

    //clickign on add button
    public void onAddButtonClick(View view){
        //checking if the entries are empty
        if(itemNameEditText.getText().toString().isEmpty() || itemPriceEditText.getText().toString().isEmpty()){
            //checking for individual cases

            String errorMessage = "Please enter the following information:\n";
            if(itemNameEditText.getText().toString().isEmpty()){
                errorMessage += "\t- Item Name\n";
            }

            if(itemPriceEditText.getText().toString().isEmpty()){
                errorMessage += "\t- Item Price\n";
            }

            onErrorDialog(errorMessage);
        }else{
            //getting entries
            String currentItemName = itemNameEditText.getText().toString();
            Double currentItemPrice = Double.parseDouble(itemPriceEditText.getText().toString());

            //creating and item
            Item currentItem = new Item(currentItemName, currentItemPrice);

            //adding the item
            items.add(currentItem);

            //updating the list view
            adapter.notifyDataSetChanged();

            //clearing the text fields
            itemNameEditText.setText("");
            itemPriceEditText.setText("");
        }

    }

    //clickign on cancel button
    public void onCancelButtonClick(View view){
        startActivity(new Intent(AddReceiptItems.this, Home.class));
    }

    //clicking on next button
    public void onNextButtonClick(View view){
        if(items.size() == 0){
            onErrorDialog("Please enter some items!");
        }else{
            //passing on data to confirmation
            Intent intent = new Intent(AddReceiptItems.this, AddReceiptManualConfirm.class);

            //testing to pass the array list
            Bundle itemsBundle = new Bundle();
            itemsBundle.putSerializable("items", items);

            //sending previous data
            intent.putExtra("receiptName", receiptName);
            intent.putExtra("category", category);
            intent.putExtra("storeName", storeName);
            intent.putExtra("street", street);
            intent.putExtra("city", city);
            intent.putExtra("state", state);
            intent.putExtra("zipCode", zipCode);
            intent.putExtra("date", date);
            intent.putExtra("userID", userID);

            //sending the array list
            intent.putExtras(itemsBundle);
            startActivity(intent);
        }
    }

    //error dialog for missing fields
    public void onErrorDialog(String message){
        //creating error dialog
        AlertDialog.Builder errorBuilder = new AlertDialog.Builder(AddReceiptItems.this);
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
