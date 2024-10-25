package vn.edu.tlu.msv2051063778;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class CaiDatActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat);

        Toolbar toolbar = findViewById(R.id.toolbar_caidat);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_caidat);
        NavigationView navigationView = findViewById(R.id.nav_view_caidat);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
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
            startActivity(new Intent(this, BanhManActivity.class));
        } else if (id == R.id.nav_cai_dat) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_dang_xuat) {
            logout();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void logout() {
        // Xử lý đăng xuất tại đây
    }
}