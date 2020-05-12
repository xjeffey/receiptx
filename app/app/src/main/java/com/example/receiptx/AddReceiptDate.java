package com.example.receiptx;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddReceiptDate extends AppCompatActivity {
    Button backButton;
    Button nextButton;
    Button cancelButton;

    CalendarView calendarView;

    //previous screen data
    String receiptName;
    String category;
    String storeName;
    String street;
    String city;
    String state;
    String zipCode;

    //update the data
    String date = "";

    String userID;

    //user typing in number of items later
    EditText userInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receipt_date);

        userID = getIntent().getStringExtra("userID");
        System.out.println(userID);

        //INITIAL SET UP
        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);
        cancelButton = findViewById(R.id.cancelButton);

        //getting current date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();

        //setting the date
        date = formatter.format(currentDate);

        //calender widget
        calendarView = findViewById(R.id.calendarView);

        //changing the date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = year + "-" + (month + 1) + "-" + dayOfMonth;
//                Log.d("Calender Activity", "onSelectedDayChange: " + date);
            }
        });

        //TESTING DATA PASSING
        receiptName = getIntent().getStringExtra("receiptName");
        category = getIntent().getStringExtra("category");
        storeName = getIntent().getStringExtra("storeName");
        street = getIntent().getStringExtra("street");
        city = getIntent().getStringExtra("city");
        state = getIntent().getStringExtra("state");
        zipCode = getIntent().getStringExtra("zipCode");
    }

    //clicking on cancel button
    public void onCancelButtonClick(View view){
        startActivity(new Intent(AddReceiptDate.this, Home.class));
    }

    //clicking on next button
    public void onNextButtonClick(View view){
        addChoiceDialog();
        //SEND INFORMATION OVER
//        startActivity(new Intent(AddReceiptDate.this, AddReceiptDate.class));

        //ALERT DIALOG INTO ALERT DIALOG
    }

    //choice to add image or manual item entry
    public void addChoiceDialog(){
        AlertDialog.Builder choiceDialog = new AlertDialog.Builder(AddReceiptDate.this);
        choiceDialog.setMessage("How would you like to add the items?").setCancelable(false)
                //clicking on image capture
                .setPositiveButton("Image Capture", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AddReceiptDate.this, ImageCapture.class);
                        intent.putExtra("receiptName", receiptName);
                        intent.putExtra("category", category);
                        intent.putExtra("storeName", storeName);
                        intent.putExtra("street", street);
                        intent.putExtra("city", city);
                        intent.putExtra("state", state);
                        intent.putExtra("zipCode", zipCode);
                        intent.putExtra("date", date);
                        intent.putExtra("userID", userID);
                        startActivity(intent);
                    }
                })
                //clicking on manual
                .setNegativeButton("Manually", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AddReceiptDate.this, AddReceiptItems.class);
                        intent.putExtra("receiptName", receiptName);
                        intent.putExtra("category", category);
                        intent.putExtra("storeName", storeName);
                        intent.putExtra("street", street);
                        intent.putExtra("city", city);
                        intent.putExtra("state", state);
                        intent.putExtra("zipCode", zipCode);
                        intent.putExtra("date", date);
                        intent.putExtra("userID", userID);
                        startActivity(intent);
                    }
                });

        AlertDialog choice = choiceDialog.create();
        choice.setTitle("Adding Items");
        choice.show();
    }
}
