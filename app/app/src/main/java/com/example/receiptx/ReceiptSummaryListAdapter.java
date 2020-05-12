package com.example.receiptx;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class ReceiptSummaryListAdapter extends ArrayAdapter<ReceiptSummary> {
    private static final String TAG = "ReceiptSummaryListAdapter";

    private Context mContext;
    int mResource;

    public ReceiptSummaryListAdapter(Context context, int resource, ArrayList<ReceiptSummary> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //getting the data to work with
        final Integer rID = getItem(position).getrID();
        final String rName = getItem(position).getrName();
        final String rStore = getItem(position).getrStore();
        final String rDate = getItem(position).getrDate();
        final String rCategory = getItem(position).getrCategory();
        final String rAddress = getItem(position).getrAddress();
        final Double rTotal = getItem(position).getrTotal();
        final String rImagePath = getItem(position).getrImagePath();

        ReceiptSummary receiptSummary = new ReceiptSummary(rID, rName, rStore, rDate, rCategory, rAddress, rTotal, rImagePath);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        //getting the text views to work with

        TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        TextView storeTextView = (TextView) convertView.findViewById(R.id.storeTextView);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
        final TextView totalTextView = (TextView) convertView.findViewById(R.id.totalTextView);

        //button click
        Button detailsButton = (Button) convertView.findViewById(R.id.detailsButton);
        detailsButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReceiptDetails.class);
                intent.putExtra("id", Integer.toString(rID));
                intent.putExtra("name", rName);
                intent.putExtra("store", rStore);
                intent.putExtra("date", rDate);
                intent.putExtra("category", rCategory);
                intent.putExtra("address", rAddress);
                intent.putExtra("total", Double.toString(rTotal));
                intent.putExtra("imagePath", rImagePath);
                mContext.startActivity(intent);
            }
        });


        //updating data for user to see
        nameTextView.setText("Title: " + rName);
        storeTextView.setText("Store: " + rStore);
        dateTextView.setText("Date: " + rDate);
        totalTextView.setText("Total: $" + Double.toString(rTotal));

        return convertView;
    }
}
