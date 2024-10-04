package com.example.prac03;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CountryAdapter countryAdapter;
    private List<CountryItem> countryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        countryList = new ArrayList<>();
        // Thêm dữ liệu mô phỏng
        countryList.add(new CountryItem(
                "2,973,190 KM2",
                "481 people/Km2",
                "New Delhi",
                R.drawable.india,
                "India",
                "1,428.6 million people",
                "17.76 %"
        ));
        countryList.add(new CountryItem(
                "9,596,961 KM2",
                "153 people/Km2",
                "Beijing",
                R.drawable.china,
                "China",
                "1,425.7 million people",
                "17.72 %"
        ));
        countryList.add(new CountryItem(
                "9,833,517 KM2",
                "36 people/Km2",
                "Washington, D.C.",
                R.drawable.us,
                "United States",
                "334.2 million people",
                "4.25 %"
        ));
        countryList.add(new CountryItem(
                "1,904,569 KM2",
                "145 people/Km2",
                "Jakarta",
                R.drawable.indonesia,
                "Indonesia",
                "277.5 million people",
                "3.47 %"
        ));
        countryList.add(new CountryItem(
                "881,913 KM2",
                "273 people/Km2",
                "Islamabad",
                R.drawable.pakistan,
                "Pakistan",
                "240.4 million people",
                "2.99 %"
        ));
        countryList.add(new CountryItem(
                "923,768 KM2",
                "242 people/Km2",
                "Abuja",
                R.drawable.nigeria,
                "Nigeria",
                "223.8 million people",
                "2.80 %"
        ));
        countryList.add(new CountryItem(
                "8,515,767 KM2",
                "25 people/Km2",
                "Brasília",
                R.drawable.brazil,
                "Brazil",
                "216.4 million people",
                "2.71 %"
        ));
        countryList.add(new CountryItem(
                "148,460 KM2",
                "1,165 people/Km2",
                "Dhaka",
                R.drawable.bangladesh,
                "Bangladesh",
                "172.9 million people",
                "2.16 %"
        ));

        countryAdapter = new CountryAdapter(countryList, countryItem -> {
            Intent intent = new Intent(MainActivity.this, CountryDetailActivity.class);
            intent.putExtra("name", countryItem.getName());
            intent.putExtra("description", countryItem.getDescription());
            intent.putExtra("image", countryItem.getImageResourceId());
            intent.putExtra("population", countryItem.getPopulation());
            intent.putExtra("area", countryItem.getArea());
            intent.putExtra("density", countryItem.getDensity());
            intent.putExtra("worldShare", countryItem.getWorldShare());
            startActivity(intent);
        });

        recyclerView.setAdapter(countryAdapter);
    }
}
