package com.example.ex14_1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends Activity {

    // Declare UI elements
    private EditText edta, edtb;
    private Button btncong;
    private ListView lv1;

    // Declare data structures
    private ArrayList<String> list;
    private ArrayAdapter<String> myarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        addControl();

        // Set click listener for button
        addEvent();
    }

    private void addEvent() {
        btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Xulycong();
            }
        });
    }

    private void Xulycong() {
        int a = Integer.parseInt(edta.getText().toString());
        int b = Integer.parseInt(edtb.getText().toString());
        String c = a + " + " + b + " = " + (a + b);

        // Update list and adapter
        list.add(c);
        myarray.notifyDataSetChanged();

        // Clear edit texts
        edta.setText("");
        edtb.setText("");
    }

    private void addControl() {
        TabHost tab = (TabHost) findViewById(R.id.tabhost);
        tab.setup();

        // Create and configure tabs
        TabHost.TabSpec tab1 = tab.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("", getResources().getDrawable(R.drawable.cong));
        tab.addTab(tab1);

        TabHost.TabSpec tab2 = tab.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("", getResources().getDrawable(R.drawable.lichsu));
        tab.addTab(tab2);

        // Find and initialize UI elements
        edta = (EditText) findViewById(R.id.edta);
        edtb = (EditText) findViewById(R.id.edtb);
        btncong = (Button) findViewById(R.id.btntong);
        lv1 = (ListView) findViewById(R.id.lv1);

        // Initialize data structures
        list = new ArrayList<>();
        myarray = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, list);
        lv1.setAdapter(myarray);
    }
}