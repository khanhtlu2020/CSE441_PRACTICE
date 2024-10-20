package com.example.ex13_autocompletetextview;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Khai báo các đối tượng giao diện người dùng
    TextView selection;
    AutoCompleteTextView singleComplete;
    MultiAutoCompleteTextView multiComplete;

    // Mảng chứa các gợi ý tự động hoàn thành
    String[] arr = {"Hà Nội", "Huế", "Sài Gòn",
            "Hà Giang", "Hội An", "Kiên Giang",
            "Lâm Đồng", "Long An"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các view từ layout
        selection = findViewById(R.id.selection);
        singleComplete = findViewById(R.id.editauto);

        // Tạo adapter để hiển thị danh sách gợi ý
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                arr
        );

        // Gán adapter cho AutoCompleteTextView
        singleComplete.setAdapter(myAdapter);

        // Lấy đối tượng MultiAutoCompleteTextView
        MultiAutoCompleteTextView multiComplete = findViewById(R.id.multiAutoCompleteTextView1);

// Thiết lập ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                arr
        );

// Gán adapter cho MultiAutoCompleteTextView
        multiComplete.setAdapter(adapter);

// Thiết lập bộ phân tích cú pháp để phân tách các gợi ý
        multiComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

// Nghe sự kiện thay đổi văn bản trong singleComplete
        singleComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Code thực hiện trước khi văn bản thay đổi
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                selection.setText(singleComplete.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Code thực hiện sau khi văn bản thay đổi
            }
        });
    }
}