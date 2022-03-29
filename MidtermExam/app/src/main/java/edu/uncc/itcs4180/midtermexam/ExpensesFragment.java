package edu.uncc.itcs4180.midtermexam;

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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpensesFragment extends Fragment implements  ExpenceRecyclerViewAdapter.IExpenseRecycler{
    private static final String TAG = "midterm-ExpensesFragment";
    private static final String ARG_EXPENSELIST = "ARG_EXPENSELIST";

    private TextView expensesFragment_noOfRecords_Number;
    private TextView expensesFragment_totalExpenses_Number;
    private Button expensesFragment_expensesSummary_Button;
    private Button expensesFragment_addExpense_Button;
    private RecyclerView expensesFragment_RecyclerView;
    LinearLayoutManager layoutManager;
    ExpenceRecyclerViewAdapter expenseRecyclerViewAdapter;


    private ArrayList<Expense> expenseArrayList;
    private double totalExpenses;

    public ExpensesFragment() {
        // Required empty public constructor
    }

    public static ExpensesFragment newInstance(ArrayList<Expense> expenseArrayList) {
        ExpensesFragment fragment = new ExpensesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXPENSELIST, expenseArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            expenseArrayList = (ArrayList<Expense>) getArguments().getSerializable(ARG_EXPENSELIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);

        // Initialize globals
        expensesFragment_noOfRecords_Number = view.findViewById(R.id.expensesFragment_noOfRecords_Number);
        expensesFragment_totalExpenses_Number = view.findViewById(R.id.expensesFragment_totalExpenses_Number);
        expensesFragment_expensesSummary_Button = view.findViewById(R.id.expensesFragment_expensesSummary_Button);
        expensesFragment_addExpense_Button = view.findViewById(R.id.expensesFragment_addExpense_Button);
        expensesFragment_RecyclerView = view.findViewById(R.id.expensesFragment_RecyclerView);
        expensesFragment_RecyclerView.setHasFixedSize(true);

        initializeLayout();

        // On expensesFragment_expensesSummary_Button press
        expensesFragment_expensesSummary_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "expensesFragment_expensesSummary_Button: onClick");
            }
        });

        // On expensesFragment_addExpense_Button press
        expensesFragment_addExpense_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "expensesFragment_addExpense_Button: onClick");
                mListener.setCurrentFragment(2);
            }
        });

        return view;
    }

    // Initializes layout values
    private void initializeLayout() {
        totalExpenses = 0.0;

        if (expenseArrayList == null || expenseArrayList.isEmpty()) {
            Log.d(TAG, "initializeList: expenseArrayList is empty");
            expensesFragment_noOfRecords_Number.setText("0");
            expensesFragment_totalExpenses_Number.setText("$0.00");
        } else {
            layoutManager = new LinearLayoutManager(getContext());
            expensesFragment_RecyclerView.setLayoutManager(layoutManager);

            expenseRecyclerViewAdapter = new ExpenceRecyclerViewAdapter(expenseArrayList, this);
            expensesFragment_RecyclerView.setAdapter(expenseRecyclerViewAdapter);

            Log.d(TAG, "initializeList: expenseArrayList size: " + expenseArrayList.size());
            for (int i = 0; i < expenseArrayList.size(); i++) {
                totalExpenses += expenseArrayList.get(i).getAmount();
            }
            expensesFragment_noOfRecords_Number.setText(expenseArrayList.size() + "");
            expensesFragment_totalExpenses_Number.setText(totalExpenses + "");
        }
    }

    @Override
    public void deleteExpense(Expense expense) {
        Log.d(TAG, "deleteExpense: " + expense.getName());
        mListener.deleteExpense(expense);
        expenseArrayList.remove(expense);
        initializeLayout();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof ExpensesFragment.IExpenseFragment) {
            mListener = (ExpensesFragment.IExpenseFragment) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    IExpenseFragment mListener;
    interface IExpenseFragment {
        void setCurrentFragment(int fragment);
        void deleteExpense(Expense expense);
    }
}