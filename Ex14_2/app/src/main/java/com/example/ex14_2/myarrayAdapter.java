package com.example.ex14_2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class myarrayAdapter extends ArrayAdapter<Item> {

    private Activity context;
    private ArrayList<Item> myArray;
    private int layoutId;

    public myarrayAdapter(Activity context, int layoutId, ArrayList<Item> arr) {
        super(context, layoutId, arr);
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);

        Item myItem = myArray.get(position);
        ImageView btnLike = convertView.findViewById(R.id.btnlike);
        int thich = myItem.getThich();

        if (thich == 1) {
            btnLike.setImageResource(R.drawable.like);
        } else {
            btnLike.setImageResource(R.drawable.unlike);
        }

        TextView tieude = convertView.findViewById(R.id.txttieude);
        tieude.setText(myItem.getTieude());
        TextView maso = convertView.findViewById(R.id.txtmaso);
        maso.setText(myItem.getMaso());

        return convertView;
    }
}

