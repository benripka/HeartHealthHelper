package com.example.simpleparadox.listycity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.OnFragmentInteractionListener {

    // Declare the variables so that you will be able to reference it later.
    ListView cityList;
    ArrayAdapter<City> cityAdapter;
    ArrayList<City> cityArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);

        String []cities ={"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin"};

        String [] provinces = {"AB", "BC", "ON", "ON", "WP"};


        cityArrayList = new ArrayList<>();

        for(int i = 0 ; i < cities.length; i++) {
            cityArrayList.add(new City(cities[i], provinces[i]));
        }

        cityAdapter = new CustomList(this, cityArrayList);

        cityList.setAdapter(cityAdapter);

        cityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                City currentCity = cityArrayList.get(position);

                Bundle bundle = new Bundle();
                String meessage = "Stackoverflow is cool!";
                bundle.putSerializable("current_city", (Serializable) currentCity);
                AddCityFragment fragment = new AddCityFragment();
                fragment.setArguments(bundle);

                fragment.show(getSupportFragmentManager(), "ADD_CITY");
                return true;
            }
        });

        final FloatingActionButton addCitybutton = findViewById(R.id.add_city_button);
        addCitybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddCityFragment().show(getSupportFragmentManager(), "ADD_CITY");
            }
        });
    }

    @Override
    public void onAddCityPressed(City newCity) {
        cityAdapter.add(newCity);
    }

    @Override
    public void onEditCityPresser(City oldCity, City newCity) {
        cityAdapter.remove(oldCity);
        cityAdapter.add(newCity);
    }
}
