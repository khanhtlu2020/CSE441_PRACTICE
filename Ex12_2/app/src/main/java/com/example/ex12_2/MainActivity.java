package com.example.ex12_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {
    ListView lv;
    ArrayList<String> arraywork;
    ArrayAdapter<String> arrAdapter;
    EditText edtwork, edth, edtm;
    TextView txtdate;
    Button btnwork;

    private static final String PREFS_NAME = "WorkPrefs";
    private static final String KEY_WORK_LIST = "work_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các thành phần từ layout
        edth = findViewById(R.id.edthour);
        edtm = findViewById(R.id.edtmi);
        edtwork = findViewById(R.id.edtwork);
        btnwork = findViewById(R.id.btnadd);
        lv = findViewById(R.id.listView1);
        txtdate = findViewById(R.id.txtdate);

        // Khởi tạo mảng ArrayList kiểu String rỗng để lưu công việc
        arraywork = new ArrayList<>();

        // Lấy dữ liệu đã lưu từ SharedPreferences
        loadData();

        // Khởi tạo ArrayAdapter và đưa mảng dữ liệu vào ArrayAdapter
        arrAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arraywork);

        // Đưa Adapter có dữ liệu lên ListView
        lv.setAdapter(arrAdapter);

        // Lấy ngày giờ hệ thống và định dạng theo định dạng dd/MM/yyyy
        Date currentDate = Calendar.getInstance().getTime();
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");
        txtdate.setText("Hôm Nay: " + simpleDateFormat.format(currentDate));

        // Xử lý sự kiện khi Click vào Button btnwork
        btnwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtwork.getText().toString().isEmpty() || edth.getText().toString().isEmpty() || edtm.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Info missing");
                    builder.setMessage("Please enter all information of the work");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Xử lý khi người dùng nhấn vào nút "Continue"
                        }
                    });
                    builder.show();
                } else {
                    String str = edtwork.getText().toString() + " - " + edth.getText().toString() + ":" + edtm.getText().toString();
                    arraywork.add(str);
                    arrAdapter.notifyDataSetChanged();
                    saveData();

                    // Xóa nội dung trong các EditText sau khi thêm
                    edth.setText("");
                    edtm.setText("");
                    edtwork.setText("");
                }
            }
        });

        // Xử lý sự kiện khi click vào item trong ListView để xóa công việc
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Xóa công việc tại vị trí được click
                arraywork.remove(position);
                arrAdapter.notifyDataSetChanged();
                saveData();
            }
        });
    }

    // Phương thức lưu dữ liệu vào SharedPreferences
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arraywork);
        editor.putString(KEY_WORK_LIST, json);
        editor.apply();
    }

    // Phương thức tải dữ liệu từ SharedPreferences
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String json = sharedPreferences.getString(KEY_WORK_LIST, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        if (json != null) {
            arraywork = gson.fromJson(json, type);
        } else {
            arraywork = new ArrayList<>();
        }
    }
}

