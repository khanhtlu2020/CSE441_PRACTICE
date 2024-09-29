package com.example.ex04;

import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import java.text.DecimalFormat;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends Activity {
EditText edtdoC,edtdoF;
Button btncf,btnfc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtdoC = findViewById(R.id.txtCel);
        edtdoF = findViewById(R.id.txtFar);
        btncf = findViewById(R.id.btnFar);
        btnfc = findViewById(R.id.btnCel);
        btncf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DecimalFormat dcf=new DecimalFormat("#.00");
                String doC = edtdoC.getText()+"";
                int C=Integer.parseInt(doC);
                edtdoF.setText(""+dcf.format(C*1.8+32));
            }
        });
        btnfc.setOnClickListener(new  View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                DecimalFormat dcf=new DecimalFormat("#.00");
                // TODO Auto-generated method stub
                String doF = edtdoF.getText()+"";
                int F=Integer.parseInt(doF);
                edtdoC.setText(""+dcf.format((F-32)/1.8));
            }
        });
    }
}