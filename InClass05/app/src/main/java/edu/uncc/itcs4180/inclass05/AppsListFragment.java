package edu.uncc.itcs4180.inclass05;

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

/**
 * Assignment #: InClass05
 * FileName: AppsListFragment.java
 * CJ D'Autorio, Mason Pipkin
 */
public class AppsListFragment extends Fragment {
    private final static String TAG = "inclass05-AppsListFragment";
    private final static String ARG_CAT = "ARG_CAT";
    private String category = "";
    ListView appsList_listView;
    AppAdapter adapter;

    public AppsListFragment() {
        // Required empty public constructor
    }


    public static AppsListFragment newInstance(String category) {
        AppsListFragment fragment = new AppsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CAT, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(ARG_CAT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apps_list, container, false);

        // Initialize globals
        appsList_listView = view.findViewById(R.id.appsList_listView);
        populateAppList();
        Log.d(TAG, "onCreateView: Category: " + category);

        // On list click
        appsList_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onCreateView: appsList_listView.setOnItemClickListener: Selected app: " + adapter.getItem(i).name);
                mListener.setCurrentApp(adapter.getItem(i));
                mListener.setCurrentFragment(2);
            }
        });

        return view;
    }

    private void populateAppList() {
        Log.d(TAG, "populateAppList: Checking category: " + category);
        ArrayList<DataServices.App> appList = DataServices.getAppsByCategory(category);
//        ArrayList<String> appStringList = new ArrayList<>();
//        for (int i = 0; i < appList.size(); i++) {
//            appStringList.add(appList.get(i).toString());
//        }
        adapter = new AppAdapter(getContext(), R.layout.app_row_item, appList);
        appsList_listView.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AppsListFragment.IListener) {
            mListener = (AppsListFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    AppsListFragment.IListener mListener;
    public interface IListener {
        void setCurrentFragment(int fragment);
        void setCurrentApp(DataServices.App app);
    }
}