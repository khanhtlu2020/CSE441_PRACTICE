package com.example.ex13_gridview;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declare an array to store data (replace with ArrayList if needed)
    private String[] arr = {
            "Ipad", "Iphone", "New Ipad",
            "Samsung", "Nokia", "Sony Ericsson",
            "LG", "Q-Mobile", "HTC", "Blackberry",
            "G Phone", "FPT-Phone", "HK Phone"
    };

    private TextView selection;
    private GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        selection = findViewById(R.id.selection);
        gv = findViewById(R.id.gridView1);

        // Create an ArrayAdapter using the data array
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, arr);

        // Set the adapter for the GridView
        gv.setAdapter(adapter);

        // Set on click listener for the GridView
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Update the TextView with the selected item
                selection.setText(arr[position]);
            }
        });
    }
}