package edu.uncc.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherRecyclerViewAdapter.WeatherViewHolder> {
    ArrayList<Forecast> forecasts;

    public WeatherRecyclerViewAdapter(ArrayList<Forecast> data){
        this.forecasts = data;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forcast_layout, parent, false);
        WeatherViewHolder weatherViewHolder = new WeatherViewHolder(view);
        return weatherViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {

        Forecast forecast = forecasts.get(position);

        holder.temp.setText(forecast.temp + " F");
        holder.max.setText(forecast.maxTemp + " F");
        holder.min.setText(forecast.minTemp + " F");
        holder.humidity.setText(forecast.humidity + " %");
        holder.cloudiness.setText(forecast.cloudiness);
        holder.date.setText(forecast.date);

    }

    @Override
    public int getItemCount() {
        return this.forecasts.size();
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder{

        TextView temp, max, min, humidity, cloudiness, date;

        public WeatherViewHolder(@NonNull View itemView) {

            super(itemView);

            temp = itemView.findViewById(R.id.temp_temp_textView);
            max = itemView.findViewById(R.id.temp_max_textView);
            min = itemView.findViewById(R.id.temp_mix_textView);
            humidity = itemView.findViewById(R.id.temp_humidity_textView);
            cloudiness = itemView.findViewById(R.id.cloudiness_textView);
            date = itemView.findViewById(R.id.date_textView);

        }
    }
}
