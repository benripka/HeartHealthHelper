package com.example.listycity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    FloatingActionButton newCityButton;
    EditText newCityField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        newCityButton = findViewById(R.id.add_city_button);
        newCityField = findViewById(R.id.new_city_field);
        cityList = findViewById(R.id.city_list);

        newCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addCity();
                newCityField.setText("");
            }
        });

        String [] cities = {"one", "two", "three"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);

        cityList.setAdapter(cityAdapter);

        cityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete this city?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dataList.remove(positionToRemove);
                        cityAdapter.notifyDataSetChanged();
                    }});
                adb.show();
                return true;
            }
            });
        }

    private void addCity() {
        String newCityText = newCityField.getText().toString().trim();

        if(newCityText.isEmpty()) {
            Toast.makeText(MainActivity.this, "Name field required", Toast.LENGTH_SHORT).show();
        } else {
            dataList.add(newCityText);
            cityAdapter.notifyDataSetChanged();
        }
    }
}
