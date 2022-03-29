package edu.uncc.weather;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.uncc.weather.databinding.FragmentCurrentWeatherBinding;

public class CurrentWeatherFragment extends Fragment {
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private DataService.City mCity;
    FragmentCurrentWeatherBinding binding;
    TextView cityTitle;
    Button gotoForecast;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    public static CurrentWeatherFragment newInstance(DataService.City city) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (DataService.City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Current Weather");
        cityTitle = getActivity().findViewById(R.id.cityTitle_textView);
        gotoForecast = getActivity().findViewById(R.id.goto_forecast_button);

        cityTitle.setText(mCity.getCity());

        gotoForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bListener.gotoWeatherForecast(mCity);
            }
        });
    }

    CurrentWeatherListener bListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        bListener = (CurrentWeatherListener) context;
    }

    interface CurrentWeatherListener {
        void gotoWeatherForecast(DataService.City city);
    }
}