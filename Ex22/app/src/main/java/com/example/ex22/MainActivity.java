package com.example.ex22;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    Button btnparse;
    ListView lv1;
    ArrayAdapter<String> myadapter;
    ArrayList<String> mylist;
    String URL = "https://randomuser.me/api/?format=xml";
    ExecutorService executorService;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnparse = findViewById(R.id.btnparse);
        lv1 = findViewById(R.id.lv1);

        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv1.setAdapter(myadapter);

        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        btnparse.setOnClickListener(v -> {
            // Sử dụng ExecutorService để chạy tác vụ trong nền
            executorService.execute(() -> {
                ArrayList<String> result = loadXmlFromUrl();
                // Cập nhật giao diện người dùng trên luồng chính sau khi xử lý xong
                handler.post(() -> {
                    myadapter.clear();
                    myadapter.addAll(result);
                });
            });
        });
    }

    private ArrayList<String> loadXmlFromUrl() {
        ArrayList<String> list = new ArrayList<>();
        try {
            // Tạo đối tượng Parser để chứa dữ liệu từ file XML
            XmlPullParserFactory fc = XmlPullParserFactory.newInstance();
            XmlPullParser parser = fc.newPullParser();

            // Tạo mới và gọi đến phương thức getXmlFromUrl(URL)
            XMLParser myparser = new XMLParser();
            String xml = myparser.getXmlFromUrl(URL); // getting XML from URL

            // Copy dữ liệu từ String xml vào đối tượng parser
            parser.setInput(new StringReader(xml));

            // Bắt đầu duyệt parser
            int eventType = -1;
            String nodeName;
            String datashow = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {
                eventType = parser.next();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        nodeName = parser.getName();
                        if (nodeName.equals("title")) {
                            datashow += parser.nextText() + " ";
                        } else if (nodeName.equals("first")) {
                            datashow += parser.nextText() + " ";
                        } else if (nodeName.equals("last")) {
                            datashow += parser.nextText() + "\n";
                        } else if (nodeName.equals("gender")) {
                            datashow += "Gender: " + parser.nextText() + "\n";
                        } else if (nodeName.equals("email")) {
                            datashow += "Email: " + parser.nextText() + "\n";
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        nodeName = parser.getName();
                        if (nodeName.equals("user")) {
                            list.add(datashow);
                            datashow = "";
                        }
                        break;
                }
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}

