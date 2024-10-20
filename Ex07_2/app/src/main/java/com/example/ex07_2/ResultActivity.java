package com.example.ex07_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity{
    TextView txt_nghiem;
    Button btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        txt_nghiem = findViewById(R.id.txt_nghiem);
        btnback = findViewById(R.id.btnback);
        Intent myintent = getIntent();
        Bundle mybundle = myintent.getBundleExtra("mypackage");
        int a = mybundle.getInt("soa");
        int b = mybundle.getInt("sob");
        String nghiem = "";
        if(a==0 && b==0)
        {
            nghiem="Vô số nghiệm";
        }
        else if(a==0 && b !=0)
        {
            nghiem="Vô nghiệm";
        }
        else
        {
            DecimalFormat dcf=new DecimalFormat("0.##");
            nghiem=dcf.format(-b*1.0/a);
        }
        txt_nghiem.setText(nghiem);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
        });
    }
}
