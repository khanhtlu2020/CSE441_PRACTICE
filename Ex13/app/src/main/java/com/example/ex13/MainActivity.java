package com.example.ex13;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
public class MainActivity extends AppCompatActivity {
    //Khai báo ListView
    String namephone[] ={"Điện thoại Iphone 12", "Điện thoại SamSung S20","Điện thoại Nokia 6","Điện thoại Bphone 2020","Điện thoại Oppo 5","Điện thoại VSmart joy2"};
    int imagephone[] = {R.drawable.ip,R.drawable.samsung,R.drawable.nokia,R.drawable.bphone, R.drawable.oppo,R.drawable.vsmart};
    ArrayList<phone> mylist; // Khai báo mảng chính
    MyArrayAdapter myadapter; //Khai báo Adapter
    ListView lv; //Khai báo Listview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        for (int i = 0; i <namephone.length;i++)
        {
            mylist.add(new phone(namephone[i],imagephone[i]));
        }
        myadapter  =  new  MyArrayAdapter(this,R.layout.layout_listview,mylist);
        lv.setAdapter(myadapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                    Intent myintent = new Intent(MainActivity.this,SubActivity.class);
                    myintent.putExtra("name",namephone[position]);
                    startActivity(myintent);
            }
        });
    }
}
