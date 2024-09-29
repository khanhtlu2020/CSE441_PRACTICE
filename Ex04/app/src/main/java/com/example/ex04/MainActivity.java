package com.example.ex04;

import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import java.text.DecimalFormat;

public class MainActivity extends Activity {
EditText edtdoC,edtdoF;
Button btncf,btnfc,btncl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtdoC = findViewById(R.id.edtdoC);
        edtdoF = findViewById(R.id.edtdoF);
        btncf = findViewById(R.id.btncf);
        btnfc = findViewById(R.id.btnfc);
        btncl = findViewById(R.id.btncl);
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
        btncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtdoC.setText("");
                edtdoF.setText("");
            }
        });
    }
}