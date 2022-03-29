package edu.uncc.itcs4180.homework04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class FilterByStateFragment extends Fragment {
    private final static String TAG = "homework04-FilterByStateFragment";
    private ListView filterState_listView;
    HashSet<String> knownStates;
    ArrayAdapter<String> adapter;

    public FilterByStateFragment() {
        // Required empty public constructor
    }

    public static FilterByStateFragment newInstance() {
        FilterByStateFragment fragment = new FilterByStateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter_by_state, container, false);

        // Initialize globals
        filterState_listView = view.findViewById(R.id.filterState_listView);
        knownStates = new HashSet<>();
        populateStateList();

        // On ListView item press
        filterState_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    mListener.setCurrentState("");
                } else {
                    mListener.setCurrentState(adapter.getItem(i));
                }
                Log.d(TAG, "State selected: " + adapter.getItem(i));
                mListener.setCurrentFragment(0);
            }
        });

        return view;
    }

    private void populateStateList() {
        // Add states to HashSet
        for (int i = 0; i < DataServices.getAllUsers().size(); i++) {
            if (!knownStates.contains(DataServices.getAllUsers().get(i).state)) {
                knownStates.add(DataServices.getAllUsers().get(i).state);
            }
        }

        // Generate sorted ArrayList based off HashSet
        ArrayList<String> knownStatesList = new ArrayList<>(knownStates);
        Collections.sort(knownStatesList, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareToIgnoreCase(t1);
            }
        });
        knownStatesList.add(0, getActivity().getResources().getString(R.string.all_states));

        // Populate listView
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,
                android.R.id.text1, knownStatesList);
        filterState_listView.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof FilterByStateFragment.IListener) {
            mListener = (FilterByStateFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    FilterByStateFragment.IListener mListener;
    public interface IListener {
        void setCurrentFragment(int fragment);
        void setCurrentState(String state);
    }
}