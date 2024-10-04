package com.example.prac03;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CountryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        TextView foodNameTextView = findViewById(R.id.food_name_detail);
        TextView foodDescriptionTextView = findViewById(R.id.country_description_detail);
        ImageView foodImageView = findViewById(R.id.food_image_detail);
        TextView tv_population = findViewById(R.id.tv_population);
        TextView tv_area = findViewById(R.id.tv_area);
        TextView tv_world = findViewById(R.id.tv_world);
        TextView tv_density = findViewById(R.id.tv_density);

        // Nhận dữ liệu từ intent
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        int imageResourceId = getIntent().getIntExtra("image", 0);
        String population = getIntent().getStringExtra("population");
        String area = getIntent().getStringExtra("area");
        String worldShare = getIntent().getStringExtra("worldShare");
        String density = getIntent().getStringExtra("density");

        // Cập nhật giao diện với thông tin nhận được
        foodNameTextView.setText(name);
        foodDescriptionTextView.setText(description);
        foodImageView.setImageResource(imageResourceId);
        tv_population.setText(population);
        tv_area.setText(area);
        tv_world.setText(worldShare);
        tv_density.setText(density);
    }
}
