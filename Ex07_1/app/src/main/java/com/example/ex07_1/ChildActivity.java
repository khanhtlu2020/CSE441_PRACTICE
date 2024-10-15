package com.example.ex07_1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChildActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        Button btn = (Button) findViewById(R.id.button1);
        TextView txt1 = (TextView) findViewById(R.id.textView1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
    // TODO Auto-generated method stub Intent intent1 = new
                Intent intent1 = new Intent(ChildActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}
