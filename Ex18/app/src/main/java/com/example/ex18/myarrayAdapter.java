package com.example.ex18;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class myarrayAdapter extends ArrayAdapter<Item> {
    Activity context = null;
    ArrayList<Item> myArray = null;
    int layoutId;

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

        final Item myItem = myArray.get(position);

        final TextView tieude = (TextView) convertView.findViewById(R.id.txttieude);
        tieude.setText(myItem.getTieude());

        final TextView maso = (TextView) convertView.findViewById(R.id.txtmaso);
        maso.setText(myItem.getMaso());

        final ImageView btnLike = (ImageView) convertView.findViewById(R.id.btnlike);
        final ImageView btnUnlike = (ImageView) convertView.findViewById(R.id.btnunlike);

        int thich = myItem.getThich();

        // Xử lý hiển thị cho ImageButton btnlike và btnunlike
        if (thich == 0) {
            btnLike.setVisibility(View.INVISIBLE); // Ẩn btnlike
            btnUnlike.setVisibility(View.VISIBLE); // Hiển thị btnunlike
        } else {
            btnUnlike.setVisibility(View.INVISIBLE); // Ẩn btnunlike
            btnLike.setVisibility(View.VISIBLE); // Hiển thị btnlike
        }

        // Xử lý sự kiện khi click vào ImageButton btnlike
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("YEUTHICH", 0);
                MainActivity.database.update("ArirangSongList", values, "MABH=?", new String[]{maso.getText().toString()});
                btnLike.setVisibility(View.INVISIBLE);
                btnUnlike.setVisibility(View.VISIBLE);
            }
        });

        // Xử lý sự kiện khi click vào ImageButton btnunlike
        btnUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("YEUTHICH", 1);
                MainActivity.database.update("ArirangSongList", values, "MABH=?", new String[]{maso.getText().toString()});
                btnUnlike.setVisibility(View.INVISIBLE);
                btnLike.setVisibility(View.VISIBLE);
            }
        });

        // Xử lý sự kiện khi click vào TextView tieude
        tieude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tieude.setTextColor(Color.RED);
                maso.setTextColor(Color.RED);

                Intent intent1 = new Intent(context, ActivitySub.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("maso", maso.getText().toString());
                intent1.putExtra("package", bundle1);
                context.startActivity(intent1);
            }
        });

        return convertView;
    }
}

