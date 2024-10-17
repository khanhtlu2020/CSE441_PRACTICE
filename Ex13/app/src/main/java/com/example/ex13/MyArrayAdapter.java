package com.example.ex13;

import android.widget.ArrayAdapter;
import android.app.Activity;
import java.util.ArrayList;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<phone> {
    Activity context;
    int idlayout;
    ArrayList<phone> mylist;

    // Tạo Constructor để MainActivity gọi đến và truyền các tham số
    public MyArrayAdapter(Activity context, int idlayout, ArrayList<phone> mylist) {
        super(context, idlayout, mylist);
        this.context = context;
        this.idlayout = idlayout;
        this.mylist = mylist;
    }
    //Gọi đến hàm getView để xây dựng lại Adapter

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflactor = context.getLayoutInflater();
        convertView = myInflactor.inflate(idlayout,null);
        phone myphone = mylist.get(position);
        // Ứng với mỗi thuộc tính, ta thực hiện 2 việc
        //- Gán id
        ImageView  imgphone  =  convertView.findViewById(R.id.imgphone);
        // - Thiết lập dữ liệu
        imgphone.setImageResource(myphone.getImagephone());
        //	textview
        TextView txtnamephone = convertView.findViewById(R.id.txtnamephone);
        txtnamephone.setText(myphone.getNamephone());
        return  convertView;
    }
}

