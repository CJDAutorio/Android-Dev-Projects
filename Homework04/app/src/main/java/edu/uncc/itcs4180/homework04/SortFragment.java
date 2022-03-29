package edu.uncc.itcs4180.homework04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class SortFragment extends Fragment implements SortRecyclerViewAdapter.SortViewHolder.OnButtonClickListener{
    private final static String TAG = "homework04-FilterByStateFragment";
    private RecyclerView sort_recyclerView;
    LinearLayoutManager layoutManager;
    SortRecyclerViewAdapter adapter;

    public SortFragment() {
        // Required empty public constructor
    }

    public static SortFragment newInstance() {
        SortFragment fragment = new SortFragment();
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
        View view = inflater.inflate(R.layout.fragment_sort, container, false);

        // Initialize globals
        sort_recyclerView = view.findViewById(R.id.sort_recyclerView);
        sort_recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        sort_recyclerView.setLayoutManager(layoutManager);

        populateSortList();

        return view;
    }

    private void populateSortList() {
        ArrayList<String> sortModeArrayList = new ArrayList<>();
        sortModeArrayList.add("Age");
        sortModeArrayList.add("Name");
        sortModeArrayList.add("State");
        Log.d(TAG, "sortModeArrayList" + sortModeArrayList.toString());

        adapter = new SortRecyclerViewAdapter(sortModeArrayList);
        sort_recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof SortFragment.IListener) {
            mListener = (SortFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    SortFragment.IListener mListener;

    public interface IListener {
        void setCurrentFragment(int fragment);
        void setCurrentSort(String sortMode, boolean isAscending);
    }

    @Override
    public void onButtonClick(String sortMode, boolean isAscending) {
        mListener.setCurrentSort(sortMode, isAscending);
        Log.d(TAG, "sortMode" + sortMode + ", isAscending" + isAscending);
    }
}