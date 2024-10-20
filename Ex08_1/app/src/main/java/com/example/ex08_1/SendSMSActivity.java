package com.example.ex08_1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.net.Uri;
import android.content.Intent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

    public class SendSMSActivity extends AppCompatActivity {
        EditText edtsms;
        Button btnback2;
        ImageButton btnsendsms;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_send_smsactivity);
            edtsms = (EditText) findViewById(R.id.edtsms);
            btnback2 = (Button) findViewById(R.id.btnback2);
            btnsendsms = (ImageButton) findViewById(R.id.btnsendsms);
            btnsendsms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callintent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+edtsms.getText().toString()));
                    startActivity(callintent);
                }
            });
            btnback2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }
