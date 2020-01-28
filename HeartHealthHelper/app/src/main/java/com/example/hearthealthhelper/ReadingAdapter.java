package com.example.hearthealthhelper;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReadingAdapter extends RecyclerView.Adapter<ReadingAdapter.ReadingHolder> {
    private List<Reading> allReadings = new ArrayList<Reading>();

    @NonNull
    @Override
    public ReadingAdapter.ReadingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reading_card, parent, false);

        return new ReadingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadingHolder holder, int position) {
        Reading currentReading =allReadings.get(position);
        holder.heartRate.setText(currentReading.getHeartRate());
        holder.systolicPressure.setText(currentReading.getSystolicPressure());
        holder.diastolicPressure.setText(currentReading.getDiastolicPressure());
        holder.dateMeasured.setText(currentReading.getDateMeasured().toString());
        holder.comment.setText(currentReading.getComment());
    }

    @Override
    public int getItemCount() {
        return allReadings.size();
    }

    public void setAllReadings(List<Reading> readings)  {
        this.allReadings = readings;
    }

    public class ReadingHolder extends RecyclerView.ViewHolder {
        private TextView heartRate;
        private TextView systolicPressure;
        private TextView diastolicPressure;
        private TextView comment;
        private TextView dateMeasured;

        ReadingHolder(View itemView) {
            super(itemView);
            this.heartRate = itemView.findViewById(R.id.heart_rate);
            this.dateMeasured = itemView.findViewById(R.id.reading_date);
            this.systolicPressure = itemView.findViewById(R.id.systolic_pressure_title);
            this.diastolicPressure = itemView.findViewById(R.id.diastolic_pressure_title);
            this.comment = itemView.findViewById(R.id.reading_comment);
        }
    }
}
