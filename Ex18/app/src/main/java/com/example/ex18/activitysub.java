package com.example.ex18;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activitysub extends AppCompatActivity {
    TextView txtMaso, txtBaiHat, txtLoiBaiHat, txtTacGia;
    ImageButton btnThich, btnKhongThich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity);

        // Ánh xạ các view
        txtMaso = (TextView) findViewById(R.id.txtmaso);
        txtBaiHat = (TextView) findViewById(R.id.txtbaihat);
        txtLoiBaiHat = (TextView) findViewById(R.id.txtloibaihat);
        txtTacGia = (TextView) findViewById(R.id.txttacgia);
        btnThich = (ImageButton) findViewById(R.id.btnthich);
        btnKhongThich = (ImageButton) findViewById(R.id.btnkhongthich);

        // Nhận Intent từ MyArrayAdapter, lấy dữ liệu từ Bundle
        Intent callerIntent1 = getIntent();
        Bundle packageCaller1 = callerIntent1.getBundleExtra("package");
        String maso = packageCaller1.getString("maso");

        // Truy vấn dữ liệu từ maso nhận được, hiển thị dữ liệu
        Cursor c = MainActivity.database.rawQuery("SELECT * FROM ArirangSongList WHERE MABH LIKE '" + maso + "'", null);

        txtMaso.setText(maso);
        if (c.moveToFirst()) {
            txtBaiHat.setText(c.getString(2));  // Tên bài hát
            txtLoiBaiHat.setText(c.getString(3));  // Lời bài hát
            txtTacGia.setText(c.getString(4));  // Tác giả

            // Xử lý hiển thị trạng thái thích/không thích
            if (c.getInt(6) == 0) {
                btnThich.setVisibility(View.INVISIBLE);
                btnKhongThich.setVisibility(View.VISIBLE);
            } else {
                btnKhongThich.setVisibility(View.INVISIBLE);
                btnThich.setVisibility(View.VISIBLE);
            }
        }
        c.close();

        // Xử lý sự kiện khi click vào btnThich
        btnThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("YEUTHICH", 0);
                MainActivity.database.update("ArirangSongList", values, "MABH=?", new String[]{txtMaso.getText().toString()});
                btnThich.setVisibility(View.INVISIBLE);
                btnKhongThich.setVisibility(View.VISIBLE);
            }
        });

        // Xử lý sự kiện khi click vào btnKhongThich
        btnKhongThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put("YEUTHICH", 1);
                MainActivity.database.update("ArirangSongList", values, "MABH=?", new String[]{txtMaso.getText().toString()});
                btnKhongThich.setVisibility(View.INVISIBLE);
                btnThich.setVisibility(View.VISIBLE);
            }
        });
    }
}

