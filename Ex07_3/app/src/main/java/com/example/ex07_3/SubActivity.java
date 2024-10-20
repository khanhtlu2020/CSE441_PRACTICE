package com.example.ex07_3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SubActivity extends AppCompatActivity {
    EditText edtAA, edtBB;
    Button btnsendtong, btnsendhieu;
    Intent myintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        edtAA = findViewById(R.id.edtAA);
        edtBB = findViewById(R.id.edtBB);
        btnsendtong = findViewById(R.id.btnsendtong);
        btnsendhieu = findViewById(R.id.btnsendhieu);
        // Nhận Intent
        myintent = getIntent();
        // lấy dữ liệu khỏi Intent
        int a = myintent.getIntExtra("soa",0);
        int b = myintent.getIntExtra("sob",0);
        edtAA.setText(a+"");
        edtBB.setText(b+"");
        btnsendtong.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                //Xử lý kết quả
               int sum = a + b;
                // Đẩy kết quả trở lại Intent
               myintent.putExtra("kq",sum);
                // Trả intent trở về
               setResult(33,myintent);
                //thoát Activity này để quay về
               finish();
           }
       });
        btnsendhieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Xử lý kết quả
                int sub = a - b;
                myintent.putExtra("kq",sub);
                setResult(34, myintent);
                finish();
            }
        });
    }
}
