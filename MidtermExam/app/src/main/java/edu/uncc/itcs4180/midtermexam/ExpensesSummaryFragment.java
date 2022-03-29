package edu.uncc.itcs4180.midtermexam;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ExpensesSummaryFragment extends Fragment {

    public ExpensesSummaryFragment() {
        // Required empty public constructor
    }

    public static ExpensesSummaryFragment newInstance() {
        ExpensesSummaryFragment fragment = new ExpensesSummaryFragment();
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
        View view = inflater.inflate(R.layout.fragment_expenses_summary, container, false);



        return view;
    }
}