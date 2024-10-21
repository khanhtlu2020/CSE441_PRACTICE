package com.example.ex23;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<List> {
    private Activity context;
    private ArrayList<List> arr;
    private int layoutID;

    public MyArrayAdapter(Activity context, int layoutID, ArrayList<List> arr) {
        super(context, layoutID, arr);
        this.context = context;
        this.layoutID = layoutID;
        this.arr = arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Tạo layout cho từng dòng của ListView
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layoutID, parent, false);
        }

        // Lấy đối tượng List tại vị trí hiện tại
        final List lst = arr.get(position);

        // Ánh xạ các thành phần giao diện
        ImageView imgItem = convertView.findViewById(R.id.imgView);
        imgItem.setImageBitmap(lst.getImg());

        TextView txtTitle = convertView.findViewById(R.id.txtTitle);
        txtTitle.setText(lst.getTitle());

        TextView txtInfo = convertView.findViewById(R.id.txtInfo);
        txtInfo.setText(lst.getInfo());

        // Thiết lập sự kiện khi nhấn vào mỗi item trong ListView
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(lst.getLink()));
            context.startActivity(intent);
        });

        return convertView;
    }
}


