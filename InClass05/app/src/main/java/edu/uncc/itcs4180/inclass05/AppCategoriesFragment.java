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
 * FileName: AppCategoriesFragment.java
 * CJ D'Autorio, Mason Pipkin
 */
public class AppCategoriesFragment extends Fragment {
    private static final String TAG = "inclass05-AppCategoriesFragment";
    ListView appCat_ListView;
    ArrayAdapter<String> adapter;
    private String category = "";

    public AppCategoriesFragment() {
        // Required empty public constructor
    }

    public static AppCategoriesFragment newInstance() {
        AppCategoriesFragment fragment = new AppCategoriesFragment();
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
        View view = inflater.inflate(R.layout.fragment_app_categories, container, false);

        // Initialize globals
        appCat_ListView = view.findViewById(R.id.appCat_ListView);

        // Populate categories list
        populateCategoriesList();

        // On category click
        appCat_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onCreateView: appCat_ListView.setOnItemClickListener: Selected category: " + adapter.getItem(i));
                mListener.setCurrentCategory(adapter.getItem(i));
                mListener.setCurrentFragment(1);
            }
        });

        return view;
    }

    private void populateCategoriesList() {
        ArrayList<String> appCategories = DataServices.getAppCategories();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,
                android.R.id.text1, appCategories);
        appCat_ListView.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AppCategoriesFragment.IListener) {
            mListener = (AppCategoriesFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    AppCategoriesFragment.IListener mListener;
    public interface IListener {
        void setCurrentFragment(int fragment);
        void setCurrentCategory(String category);
    }
}