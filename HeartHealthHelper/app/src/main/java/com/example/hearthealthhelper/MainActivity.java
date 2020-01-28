package com.example.hearthealthhelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_READING_REQUEST = 1;

    private ReadingViewModel readingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton newReadingButton =findViewById(R.id.new_reading_button);

        newReadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewReadingActivity();
            }
        });

        RecyclerView readingList =findViewById(R.id.reading_list);
        readingList.setLayoutManager(new LinearLayoutManager(this));
        readingList.hasFixedSize();

        final ReadingAdapter readingAdapter = new ReadingAdapter();
        readingList.setAdapter(readingAdapter);


        readingViewModel = ViewModelProviders.of(this).get(ReadingViewModel.class);
        readingViewModel.getAllReadings().observe(this, new Observer<List<Reading>>() {
            @Override
            public void onChanged(List<Reading> readings) {
                readingAdapter.setAllReadings(readings);
            }
        });

    }
    private void startNewReadingActivity() {
        Intent intent = new Intent(this, NewReadingActivity.class);
        startActivityForResult(intent, NEW_READING_REQUEST);
    }

    private Application getCurrentApp() {
        return this.getApplication();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_READING_REQUEST && resultCode == Activity.RESULT_OK) {
            int heartRate = data.getIntExtra("heartRate", 1);
            int systolicPressure = data.getIntExtra("systolicPressure", 1);
            int diasolicPressure = data.getIntExtra("diastolicPressure", 1);
            String comment = data.getStringExtra("comment");
            Date date = (Date) data.getSerializableExtra("date");

            Reading newReading = new Reading(date, systolicPressure, diasolicPressure, heartRate, comment);
            readingViewModel.insert(newReading);
        }
    }
}
