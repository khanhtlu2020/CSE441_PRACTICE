package com.example.ex13_customgridview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String[] arrayName = {
            "Ảnh 1", "Ảnh 2", "Ảnh 3", "Ảnh 4", "Ảnh 5", "Ảnh 6",
            "Ảnh 7", "Ảnh 8", "Ảnh 9", "Ảnh 10", "Ảnh 11", "Ảnh 12"
    };

    public static int[] imageName = {
            R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6,
            R.drawable.img7, R.drawable.img8, R.drawable.img9, R.drawable.img10, R.drawable.img11, R.drawable.img12
    };

    GridView gridViewDemo;
    MyarrayAdapter adapterDanhSach;
    ArrayList<Image> arrimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ GridView từ layout
        gridViewDemo = findViewById(R.id.grid1);
        arrimage = new ArrayList<>();

        // Khởi tạo đối tượng adapter và gán Data source
        adapterDanhSach = new MyarrayAdapter(MainActivity.this, R.layout.listitem, arrimage);

        // Gán Adapter vào GridView
        gridViewDemo.setAdapter(adapterDanhSach);

        // Duyệt danh sách mảng dữ liệu và thêm vào arrimage
        for (int i = 0; i < imageName.length; i++) {
            Image myimage = new Image();
            myimage.setName(arrayName[i]);
            myimage.setImg(imageName[i]);
            arrimage.add(myimage);
            // Cập nhật giao diện sau khi thêm dữ liệu
            adapterDanhSach.notifyDataSetChanged();
        }

        // Viết sự kiện khi click vào đối tượng trong GridView
        gridViewDemo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // Khai báo Intent
                Intent intent1 = new Intent(MainActivity.this, subActivity.class);

                // Khai báo bundle và đưa dữ liệu vào Bundle
                Bundle bundle = new Bundle();
                bundle.putInt("TITLE", position);
                intent1.putExtras(bundle);

                // Chuyển sang activity con
                startActivity(intent1);
            }
        });
    }
}
