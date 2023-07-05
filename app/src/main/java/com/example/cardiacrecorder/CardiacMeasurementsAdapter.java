package com.example.cardiacrecorder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardiacMeasurementsAdapter extends
        RecyclerView.Adapter<CardiacMeasurementsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView systolicPressure;
        public TextView diastolicPressure;
        public TextView heartRate;
        public TextView date;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            date = itemView.findViewById(R.id.date);
            systolicPressure = itemView.findViewById(R.id.systolicPressure);
            diastolicPressure = itemView.findViewById(R.id.diastolicPressure);
            heartRate = itemView.findViewById(R.id.heartRate);
            time = itemView.findViewById(R.id.time);
        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();

            CardiacMeasurement cardiacMeasurement = cardiacMeasurementArrayList.get(position);

            String id = cardiacMeasurement.getId();
            int year = cardiacMeasurement.getYear();
            int month = cardiacMeasurement.getMonth();
            int date = cardiacMeasurement.getDate();
            int hour = cardiacMeasurement.getHour();
            int minute = cardiacMeasurement.getMinute();

            int sysloticPressure = cardiacMeasurement.getSystolicPressure();
            int diastolicPressure = cardiacMeasurement.getDiastolicPressure();
            int heartRate = cardiacMeasurement.getHeartRate();
            String comment = cardiacMeasurement.getComment();

            Intent intent = new Intent(context, UpdateDeleteCardiacMeasurementActivtiy.class);

            intent.putExtra("id", id);
//            intent.putExtra("date", Integer.toString(date));
//            intent.putExtra("month", Integer.toString(month));
//            intent.putExtra("year", Integer.toString(year));
//            intent.putExtra("hour", Integer.toString(hour));
//            intent.putExtra("minute", Integer.toString(minute));

            intent.putExtra("date", date);
            intent.putExtra("month", month);
            intent.putExtra("year", year);
            intent.putExtra("hour", hour);
            intent.putExtra("minute", minute);

            intent.putExtra("systolicPressure", Integer.toString(sysloticPressure));
            intent.putExtra("diastolicPressure", Integer.toString(diastolicPressure));
            intent.putExtra("heartRate", Integer.toString(heartRate));
            intent.putExtra("comment", comment);

            context.startActivity(intent);
        }
    }

    private ArrayList<CardiacMeasurement> cardiacMeasurementArrayList;
    private Context context;

    public CardiacMeasurementsAdapter(Context context, ArrayList<CardiacMeasurement> cardiacMeasurementArrayList) {
        this.context = context;
        this.cardiacMeasurementArrayList = cardiacMeasurementArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View cardiacMeasurementView = layoutInflater.inflate(R.layout.cardiac_measurement, parent, false);

        ViewHolder viewHolder = new ViewHolder(cardiacMeasurementView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardiacMeasurementsAdapter.ViewHolder holder, int position) {
        CardiacMeasurement cardiacMeasurement = cardiacMeasurementArrayList.get(position);

        TextView date = holder.date;
        TextView time = holder.time;
        TextView systolicPressure = holder.systolicPressure;
        TextView diastolicPressure = holder.diastolicPressure;
        TextView heartRate = holder.heartRate;

        String minute = Integer.toString(cardiacMeasurement.getMinute());
        if (minute.length() == 1) minute = "0" + minute;
        String hour = Integer.toString(cardiacMeasurement.getHour());
        if (hour.length() == 1) hour = "0" + hour;

        date.setText("Date: " + cardiacMeasurement.getDate() + "-" + cardiacMeasurement.getMonth() + "-" + cardiacMeasurement.getYear());
        time.setText("Time: " + hour + ":" + minute);
        systolicPressure.setText(Integer.toString(cardiacMeasurement.getSystolicPressure()));
        diastolicPressure.setText(Integer.toString(cardiacMeasurement.getDiastolicPressure()));
        heartRate.setText("Heart Rate: " + cardiacMeasurement.getHeartRate());
    }

    @Override
    public int getItemCount() {
        return cardiacMeasurementArrayList.size();
    }
}
