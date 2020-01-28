package com.example.hearthealthhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewReadingActivity extends AppCompatActivity {

    private EditText heartRateField, systolicPressureField, diastolicPressureField, commentField, dateField, timeField;
    private FloatingActionButton submitReadingButton;
    private Date dateMeasured = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_reading_activity);

        initializeComponents();

        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());

                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                int currentMonth = calendar.get(Calendar.MONTH);
                int currentYear = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewReadingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, month);
                                calendar.set(Calendar.DAY_OF_MONTH, day);
                                dateMeasured = calendar.getTime();

                                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                dateField.setText(formatter.format(dateMeasured));
                            }
                        }, currentYear, currentMonth, currentDay);
                datePickerDialog.show();
            }
        });

        timeField.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              TimePickerDialog timePickerDialog = new TimePickerDialog(NewReadingActivity.this,
                                                      new TimePickerDialog.OnTimeSetListener() {
                                                          @Override
                                                          public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                              Calendar calendar = Calendar.getInstance();
                                                              calendar.setTime(dateMeasured);
                                                              calendar.set(Calendar.MINUTE, minute);
                                                              calendar.set(Calendar.HOUR, hourOfDay);

                                                              SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
                                                              timeField.setText(formatter.format(dateMeasured));
                                                          }
                                                      }, 1, 1, false);
                                              timePickerDialog.show();
                                          }
                                      });

            submitReadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSavingReading();
            }
        });
    }

    private void initializeComponents() {
        this.heartRateField = findViewById(R.id.heart_rate_field);
        this.systolicPressureField = findViewById(R.id.systolic_pressure_field);
        this.diastolicPressureField = findViewById(R.id.diastolic_pressure_field);
        this.commentField = findViewById(R.id.comment_field);
        this.dateField = findViewById(R.id.date_field);
        this.timeField = findViewById(R.id.time_field);
        this.submitReadingButton = findViewById(R.id.submit_reading_button);
    }

    private void attemptSavingReading() {

        int heartRate = 0;
        int systolicPressure = 0;
        int diastolicPressure = 0;
        String comment;
        Boolean error = false;

        if(heartRateField.getText().toString().trim().isEmpty()) {
            heartRateField.setError("Heart rate is a mandatory field");
            error = true;
        } else {
            heartRate = Integer.parseInt(heartRateField.getText().toString().trim());
        }

        if(systolicPressureField.getText().toString().trim().isEmpty()) {
            systolicPressureField.setError("Systolic pressure is a mandatory field");
            error = true;
        } else {
            systolicPressure = Integer.parseInt(systolicPressureField.getText().toString().trim());
        }

        if(diastolicPressureField.getText().toString().trim().isEmpty()) {
            diastolicPressureField.setError("Diastolic pressure is a mandatory field");
            error = true;
        } else {
            diastolicPressure = Integer.parseInt(diastolicPressureField.getText().toString().trim());
        }

        comment = commentField.getText().toString().trim();

        Intent resultIntent = new Intent();


        if(error) {
            return;
        } else {
            resultIntent.putExtra("date", dateMeasured);
            resultIntent.putExtra("systolicPressure", systolicPressure);
            resultIntent.putExtra("diastolicPressure", diastolicPressure);
            resultIntent.putExtra("comment", comment);
            resultIntent.putExtra("heartRate", heartRate);

            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }
}
