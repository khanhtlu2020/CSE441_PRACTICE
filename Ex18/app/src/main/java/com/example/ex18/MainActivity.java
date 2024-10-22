package com.example.ex18;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    public static String DATABASE_NAME = "arirang.sqlite";
    EditText edtTim;
    ListView lv1, lv2, lv3;
    ArrayList<Item> list1, list2, list3;
    myarrayAdapter myarray1, myarray2, myarray3;
    TabHost tab;
    ImageButton btnXoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Copy CSDL arirang.sqlite
        processCopy();

        // Mở cơ sở dữ liệu đã copy
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        // Thêm các Controls
        addControl();

        // Xử lý tìm kiếm
        addTim();

        // Xử lý sự kiện khi chuyển Tab và các sự kiện khác
        addEvents();
    }

    // Hàm khai báo và thêm các Controls vào giao diện
    private void addControl() {
        btnXoa = (ImageButton) findViewById(R.id.btnxoa);
        tab = (TabHost) findViewById(R.id.tabhost);
        tab.setup();

        // Thiết lập các Tab
        TabHost.TabSpec tab1 = tab.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("", getResources().getDrawable(R.drawable.search));
        tab.addTab(tab1);

        TabHost.TabSpec tab2 = tab.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("", getResources().getDrawable(R.drawable.list));
        tab.addTab(tab2);

        TabHost.TabSpec tab3 = tab.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("", getResources().getDrawable(R.drawable.favourite));
        tab.addTab(tab3);

        edtTim = (EditText) findViewById(R.id.edttim);
        lv1 = (ListView) findViewById(R.id.lv1);
        lv2 = (ListView) findViewById(R.id.lv2);
        lv3 = (ListView) findViewById(R.id.lv3);

        // Khởi tạo danh sách và adapter
        list1 = new ArrayList<Item>();
        list2 = new ArrayList<Item>();
        list3 = new ArrayList<Item>();

        myarray1 = new myarrayAdapter(MainActivity.this, R.layout.listitem, list1);
        myarray2 = new myarrayAdapter(MainActivity.this, R.layout.listitem, list2);
        myarray3 = new myarrayAdapter(MainActivity.this, R.layout.listitem, list3);

        lv1.setAdapter(myarray1);
        lv2.setAdapter(myarray2);
        lv3.setAdapter(myarray3);
    }

    // Xử lý sự kiện khi chuyển Tab
    private void addEvents() {
        tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equalsIgnoreCase("t2")) {
                    addDanhsach();
                }
                if (tabId.equalsIgnoreCase("t3")) {
                    addYeuthich();
                }
            }
        });

        // Sự kiện khi click vào Button xóa trên Tab Tìm kiếm
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTim.setText("");
            }
        });
    }

    // Hàm thêm bài hát vào ListView trên Tab Yêu thích
    private void addYeuthich() {
        myarray3.clear();
        Cursor c = database.rawQuery("SELECT * FROM ArirangSongList WHERE YEUTHICH = 1", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            list3.add(new Item(c.getString(1), c.getString(2), c.getInt(6)));
            c.moveToNext();
        }
        c.close();
        myarray3.notifyDataSetChanged();
    }

    // Hàm thêm bài hát vào ListView trên Tab Danh sách bài hát
    private void addDanhsach() {
        myarray2.clear();
        Cursor c = database.rawQuery("SELECT * FROM ArirangSongList", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            list2.add(new Item(c.getString(1), c.getString(2), c.getInt(6)));
            c.moveToNext();
        }
        c.close();
        myarray2.notifyDataSetChanged();
    }

    // Hàm xử lý tìm kiếm bài hát theo Tiêu đề và Mã số
    private void addTim() {
        edtTim.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getdata();
            }

            private void getdata() {
                String dulieunhap = edtTim.getText().toString();
                myarray1.clear();
                if (!dulieunhap.equals("")) {
                    Cursor c = database.rawQuery("SELECT * FROM ArirangSongList WHERE TENBH1 LIKE '%" + dulieunhap + "%' OR MABH LIKE '%" + dulieunhap + "%'", null);
                    c.moveToFirst();
                    while (!c.isAfterLast()) {
                        list1.add(new Item(c.getString(1), c.getString(2), c.getInt(6)));
                        c.moveToNext();
                    }
                    c.close();
                }
                myarray1.notifyDataSetChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // Hàm xử lý Copy CSDL từ thư mục assets vào hệ thống thư mục cài đặt
    private void processCopy() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying success from Assets folder", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    public void CopyDataBaseFromAsset() {
        try {
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();

            // Tạo thư mục nếu chưa tồn tại
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists()) f.mkdir();

            // Tạo file database và ghi dữ liệu
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Đóng các stream
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
