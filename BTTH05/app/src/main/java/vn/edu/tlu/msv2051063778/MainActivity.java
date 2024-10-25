package vn.edu.tlu.msv2051063778;

import android.content.res.AssetManager;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewMon;
    ArrayList<Item> itemList;
    MyArrayAdapter adapter;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        recyclerViewMon = findViewById(R.id.recyclerViewMon);

        itemList = new ArrayList<>();
        loadItemsFromJson();

        recyclerViewMon.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyArrayAdapter(this, R.layout.item_mon, itemList);
        recyclerViewMon.setAdapter(adapter);
    }

    private void loadItemsFromJson() {
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("tlucoffee.json");
            String jsonStr = convertStreamToString(inputStream);
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObj.getJSONArray("mon");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemObj = jsonArray.getJSONObject(i);
                String maMon = itemObj.getString("MaMon");
                String tenMon = itemObj.getString("TenMon");
                Double donGia = itemObj.getDouble("DonGia");
                String hinh = itemObj.getString("Hinh");

                itemList.add(new Item(maMon, tenMon, donGia, hinh));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}

