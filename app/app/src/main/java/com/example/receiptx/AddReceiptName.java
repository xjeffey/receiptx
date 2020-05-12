package com.example.receiptx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddReceiptName extends AppCompatActivity {
    Button nextButton;
    Button cancelButton;

    EditText receiptNameEditText;

    //user id
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receipt_name);

        //testing to get userID
        userID = getIntent().getStringExtra("userID");
        System.out.println(userID);

        //initial set up
        nextButton = findViewById(R.id.nextButton);
        cancelButton = findViewById(R.id.cancelButton);

        receiptNameEditText = findViewById(R.id.receiptNameEditText);
    }

    //clicking on cancel button
    public void onCancelButtonClick(View view){
        startActivity(new Intent(AddReceiptName.this, Home.class));
    }

    //clickign on next button
    public void onNextButtonBack(View view){
        //ADD FUNCTIONALITY HERE
        //checking if edit text is empty
        if (receiptNameEditText.getText().toString().isEmpty()){
            onErrorDialog();
        }else{
            String receiptName = receiptNameEditText.getText().toString();

            Intent intent = new Intent(AddReceiptName.this, AddReceiptCategory.class);
            intent.putExtra("receiptName", receiptName);
            intent.putExtra("userID", userID);
            startActivity(intent);
        }
    }

    //error dialog for missing fields
    public void onErrorDialog(){
        //creating error dialog
        AlertDialog.Builder errorBuilder = new AlertDialog.Builder(AddReceiptName.this);
        //updating the message
        errorBuilder.setMessage("Please enter a Receipt Title!");
        //updating the title
        errorBuilder.setTitle("Missing Field(s)");
        errorBuilder.setIcon(R.drawable.error);
        //creating the dialog
        AlertDialog errorDialog = errorBuilder.create();
        //showing the dialog
        errorDialog.show();
    }
}
