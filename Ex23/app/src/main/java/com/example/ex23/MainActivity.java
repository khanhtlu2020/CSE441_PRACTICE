package com.example.ex23;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Xml;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    static ListView lv1;
    ArrayList<List> mylist;
    MyArrayAdapter myadapter;
    String nodeName;
    String title = "";
    String link = "";
    String description = "";
    String des = "";
    String urlStr = "";
    Bitmap mIcon_val = null;
    String URL = "https://vnexpress.net/rss/tin-noi-bat.rss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv1 = findViewById(R.id.lv1);
        mylist = new ArrayList<>();
        myadapter = new MyArrayAdapter(MainActivity.this, R.layout.layout_listview, mylist);
        lv1.setAdapter(myadapter);

        loadRSSFeed();
    }

    private void loadRSSFeed() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            ArrayList<List> result = doInBackground();
            handler.post(() -> {
                myadapter.clear();
                myadapter.addAll(result);
            });
        });
    }

    private ArrayList<List> doInBackground() {
        mylist = new ArrayList<>();
        try {
            XmlPullParserFactory fc = XmlPullParserFactory.newInstance();
            XmlPullParser parser = fc.newPullParser();
            XMLParser myparser = new XMLParser();
            String xml = myparser.getXmlFromUrl(URL);

            parser.setInput(new StringReader(xml));
            int eventType = -1;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                eventType = parser.next();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        nodeName = parser.getName();
                        if (nodeName.equals("title")) {
                            title = parser.nextText();
                        } else if (nodeName.equals("link")) {
                            link = parser.nextText();
                        } else if (nodeName.equals("description")) {
                            description = parser.nextText();
                            try {
                                urlStr = description.substring((description.indexOf("src=") + 5), (description.indexOf("></a") - 2));
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                            des = description.substring(description.indexOf("</br>") + 5);
                            try {
                                URL newurl = new URL(urlStr);
                                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        nodeName = parser.getName();
                        if (nodeName.equals("item")) {
                            mylist.add(new List(mIcon_val, title, des, link));
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mylist;
    }
}
