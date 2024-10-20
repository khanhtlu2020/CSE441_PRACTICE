package com.example.ex08_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import kotlinx.coroutines.channels.Send;

public class MainActivity extends AppCompatActivity {
    Button btncallphone, btnsendsms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btncallphone = findViewById(R.id.btncallphone);
        btnsendsms = findViewById(R.id.btnsendsms);
        //Xử lý click
        btncallphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Khai báo Intent công khai
                Intent myintent = new Intent(MainActivity.this, CallPhoneActivity.class);
                startActivity(myintent);
            }
        });
        btnsendsms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent2 = new Intent(MainActivity.this, SendSMSActivity.class);
                startActivity(myintent2);
            }
        });
    }
}