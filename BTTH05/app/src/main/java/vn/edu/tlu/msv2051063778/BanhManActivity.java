package vn.edu.tlu.msv2051063778;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class BanhManActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerViewMon;
    ArrayList<Item> itemList;
    MyArrayAdapter adapter;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banh_man);

        Toolbar toolbar = findViewById(R.id.toolbar_banhman);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_banhman);
        NavigationView navigationView = findViewById(R.id.nav_view_banhman);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        recyclerViewMon = findViewById(R.id.recyclerViewMon_banhman);
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
            JSONArray jsonArray = jsonObj.getJSONArray("banhman");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject itemObj = jsonArray.getJSONObject(i);
                String maMon = itemObj.getString("MaMon");
                String tenMon = itemObj.getString("TenMon");
                Double donGia = itemObj.getDouble("DonGia");
                String moTa = itemObj.getString("MoTa");
                String hinh = itemObj.getString("Hinh");

                itemList.add(new Item(maMon, tenMon, donGia, hinh, moTa));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_cafe) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.nav_nuoc_trai_cay) {
            startActivity(new Intent(this, NuocTraiCayActivity.class));
        } else if (id == R.id.nav_tra_dac_biet) {
            startActivity(new Intent(this, TraDacBietActivity.class));
        } else if (id == R.id.nav_banh_ngot) {
            startActivity(new Intent(this, BanhNgotActivity.class));
        } else if (id == R.id.nav_banh_man) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_cai_dat) {
            startActivity(new Intent(this, CaiDatActivity.class));
        } else if (id == R.id.nav_dang_xuat) {
            logout();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void logout() {
        finishAffinity();
        System.exit(0);
    }
}
