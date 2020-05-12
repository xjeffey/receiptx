package com.example.receiptx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

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

public class ReceiptDetails extends AppCompatActivity {

    //receipt info
    Integer rID;
    String rName;
    String rStore;
    String rDate;
    String rCategory;
    String rAddress;
    Double rTotal;
    String rImagePath;

    //textviews
    TextView nameTextView;
    TextView storeTextView;
    TextView categoryTextView;
    TextView addressTextView;
    TextView dateTextView;
    TextView totalTextView;

    ImageView receiptImageIV;
    //button
    Button backButton;

    //list view
    ListView itemsListView;

    //getting account that is signed in
    GoogleSignInAccount acct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_details);

        //updating the receipt info
        rID = Integer.parseInt(getIntent().getStringExtra("id"));
        rName = getIntent().getStringExtra("name");
        rStore = getIntent().getStringExtra("store");
        rDate = getIntent().getStringExtra("date");
        rCategory = getIntent().getStringExtra("category");
        rAddress = getIntent().getStringExtra("address");
        rTotal = Double.parseDouble(getIntent().getStringExtra("total"));
        rImagePath = getIntent().getStringExtra("imagePath");

        //textview
        nameTextView = findViewById(R.id.nameTextView);
        storeTextView = findViewById(R.id.storeTextView);
        categoryTextView = findViewById(R.id.categoryTextView);
        addressTextView = findViewById(R.id.addressTextView);
        dateTextView = findViewById(R.id.dateTextView);
        totalTextView = findViewById(R.id.totalTextView);

        receiptImageIV = findViewById(R.id.receiptImageIV);

        //button
        backButton = findViewById(R.id.backButton);

        //list view
        itemsListView = findViewById(R.id.itemsListView);

        updateScreen();

        //checking to see if receipt has an image
        if(!rImagePath.equalsIgnoreCase("null")){
            updateImage();
        }
        //updating items
        else{
            updateItems();
        }
    }

    //getting the email thats logged in
    public String getAccEmail(){
        String personEmail = "";

        acct = GoogleSignIn.getLastSignedInAccount(ReceiptDetails.this);

        //checking if user has signed in
        if (acct != null) {
            personEmail = acct.getEmail();
        }

        return personEmail;
    }

    //updating screen
    public void updateScreen(){
        nameTextView.setText(rName);
        storeTextView.setText(rStore);
        categoryTextView.setText(rCategory);
        addressTextView.setText(rAddress);
        dateTextView.setText(rDate);
        totalTextView.setText("Total: $" + rTotal);
    }

    //update image
    public void updateImage(){
        itemsListView.setVisibility(View.INVISIBLE);
        receiptImageIV.setVisibility(View.VISIBLE);
        receiptImageIV.setImageURI(Uri.parse(rImagePath));
    }

    //getting all the items correlated with receipt
    public void updateItems(){
//        System.out.println(getIntent().getStringExtra("id"));
//        link to accesss
        String dbConnect = "http://cgi.soic.indiana.edu/~team43/receiptx/select_all_item.php";

        //getting access to link
        OkHttpClient client = new OkHttpClient();

        //giving the api paraments to work with
        RequestBody formBody = new FormBody.Builder()
                .add("email", getAccEmail())
                .add("receciptID", getIntent().getStringExtra("id"))
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
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                else{
                    String responseData = response.body().string();

                    //checking if we got data back
                    if (responseData.equalsIgnoreCase("0 results")){

                    }
                    else{
                        try{
                            //holding all the items
                            ArrayList<Item> items = new ArrayList<>();

                            //converting the response to a JSONObject
                            final JSONObject responseJ = new JSONObject(responseData);
                            //getting the array out of the object of all receipts
                            final JSONArray responseA = responseJ.getJSONArray("allItem");

                            //looping through each receipt
                            for(int i = 0; i < responseA.length(); i++) {
                                //looking at current receipt
                                JSONObject currentItem = responseA.getJSONObject(i);

                                String itemName = currentItem.getString("item");
                                Double itemPrice = Double.parseDouble(currentItem.getString("price"));

                                Item item = new Item(itemName, itemPrice);

                                items.add(item);
                            }

                            final ArrayList<Item> itemsFinal = items;

                            ReceiptDetails.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ItemListAdapter adapter = new ItemListAdapter(ReceiptDetails.this, R.layout.items_adapter_layout, itemsFinal);
                                    itemsListView.setAdapter(adapter);
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

    //clicking on back button
    public void onBackButtonClick(View view){
        startActivity(new Intent(ReceiptDetails.this, Receipt.class));
    }
}
