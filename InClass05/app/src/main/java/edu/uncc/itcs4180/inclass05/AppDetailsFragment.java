package edu.uncc.itcs4180.inclass05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Assignment #: InClass05
 * FileName: AppDetailsFragment.java
 * CJ D'Autorio, Mason Pipkin
 */
public class AppDetailsFragment extends Fragment {
    private static final String TAG = "inclass05-AppDetailsFragment";
    private static final String ARG_APP = "ARG_APP";
    DataServices.App currentApp;
    TextView appDetails_appNameText;
    TextView appDetails_artistNameText;
    TextView appDetails_releaseDateText;
    ListView appDetails_genresList;
    ArrayAdapter<String> adapter;

    public AppDetailsFragment() {
        // Required empty public constructor
    }

    public static AppDetailsFragment newInstance(DataServices.App app) {
        AppDetailsFragment fragment = new AppDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_APP, app);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentApp = (DataServices.App) getArguments().getSerializable(ARG_APP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_details, container, false);

        // Initialize globals
        appDetails_appNameText = view.findViewById(R.id.appDetails_appNameText);
        appDetails_artistNameText = view.findViewById(R.id.appDetails_artistNameText);
        appDetails_releaseDateText = view.findViewById(R.id.appDetails_releaseDateText);
        appDetails_genresList = view.findViewById(R.id.appDetails_genresList);
        populateDetails();

        return view;
    }

    private void populateDetails() {
        appDetails_appNameText.setText(currentApp.name);
        appDetails_artistNameText.setText(currentApp.artistName);
        appDetails_releaseDateText.setText(currentApp.releaseDate);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,
                android.R.id.text1, currentApp.genres);
        appDetails_genresList.setAdapter(adapter);
    }
}