package vn.edu.tlu.msv2051063778;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ItemDetailActivity extends AppCompatActivity {
    private ImageView imgMon;
    private TextView tenMon, donGia, moTa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        imgMon = findViewById(R.id.hinh);
        tenMon = findViewById(R.id.tenMon);
        donGia = findViewById(R.id.donGia);
        moTa = findViewById(R.id.moTa);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        String ten = intent.getStringExtra("tenMon");
        double gia = intent.getDoubleExtra("giaMon", 0);
        int imgResId = intent.getIntExtra("imgResId", 0);
        String moTa1 = intent.getStringExtra("moTa");

        // Hiển thị dữ liệu lên các View
        tenMon.setText(ten);
        donGia.setText(String.format("%.0f đ", gia));
        imgMon.setImageResource(imgResId);
        moTa.setText(moTa1);
    }
}
