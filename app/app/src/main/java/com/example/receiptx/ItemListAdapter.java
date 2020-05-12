package com.example.receiptx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ItemListAdapter extends ArrayAdapter<Item> {
    private static final String TAG = "ItemListAdapter";

    private Context mContext;
    int mResource;

    public ItemListAdapter(Context context, int resource, ArrayList<Item> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //getting the data to work with
        final String itemName = getItem(position).getItemName();
        final Double itemPrice = getItem(position).getItemPrice();

        Item item = new Item(itemName, itemPrice);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView itemNameTextView = (TextView) convertView.findViewById(R.id.itemNameTextView);
        TextView itemPriceTextView = (TextView) convertView.findViewById(R.id.itemPriceTextView);

        itemNameTextView.setText(itemName);
        itemPriceTextView.setText("$" + itemPrice);

        return convertView;
    }
}
