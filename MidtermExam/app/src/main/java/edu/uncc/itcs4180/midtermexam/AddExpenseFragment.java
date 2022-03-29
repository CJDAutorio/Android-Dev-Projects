package edu.uncc.itcs4180.midtermexam;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddExpenseFragment extends Fragment {
    EditText addExpenseFragment_name_EditText;
    EditText addExpenseFragment_amount_EditText;
    TextView addExpenseFragment_date_TextView;
    TextView addExpenseFragment_cat_TextView;
    Button addExpenseFragment_date_Button;
    Button addExpenseFragment_cat_Button;
    Button addExpenseButton_cancel_Button;
    Button addExpenseButton_submit_Button;

    int day, month, year;
    String category;

    private final static String ARG_CAT = "ARG_CAT";
    private final static String TAG = "midterm-AddExpenseFragment";

    Expense expense = new Expense();

    public AddExpenseFragment() {
        // Required empty public constructor
    }

    public static AddExpenseFragment newInstance(String category) {
        AddExpenseFragment fragment = new AddExpenseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CAT, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.category = getArguments().getString(ARG_CAT);
        } else {
            this.category = "";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_expense, container, false);

        // Initialize globals
        addExpenseFragment_name_EditText = view.findViewById(R.id.addExpenseFragment_name_EditText);
        addExpenseFragment_amount_EditText = view.findViewById(R.id.addExpenseFragment_amount_EditText);
        addExpenseFragment_date_TextView = view.findViewById(R.id.addExpenseFragment_date_TextView);
        addExpenseFragment_cat_TextView = view.findViewById(R.id.addExpenseFragment_cat_TextView);
        addExpenseFragment_date_Button = view.findViewById(R.id.addExpenseFragment_date_Button);
        addExpenseFragment_cat_Button = view.findViewById(R.id.addExpenseFragment_cat_Button);
        addExpenseButton_cancel_Button = view.findViewById(R.id.addExpenseButton_cancel_Button);
        addExpenseButton_submit_Button = view.findViewById(R.id.addExpenseButton_submit_Button);

        // Updates category text if necessary
        if (!(category == null) && !category.isEmpty()) {
            addExpenseFragment_cat_TextView.setText(category);
        }

        // On addExpenseFragment_date_Button press
        addExpenseFragment_date_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "addExpenseFragment_date_Button: onClick");
                Calendar calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        expense.setDay(i2);
                        expense.setMonth(i1 + 1);
                        expense.setYear(i);
                        Log.d(TAG, "addExpenseFragment_date_Button: onClick: onDateSet:" +
                                "Expense date: " + expense.getDateToString());
                        addExpenseFragment_date_TextView.setText(expense.getDateToString());
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        // On addExpenseFragment_cat_Button press
        addExpenseFragment_cat_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "addExpenseFragment_cat_Button: onClick");
                mListener.setCurrentFragment(3);
            }
        });

        // On addExpenseButton_cancel_Button press
        addExpenseButton_cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "addExpenseButton_cancel_Button: onClick");
                mListener.setCurrentFragment(0);
            }
        });

        // On addExpenseButton_submit_Button press
        addExpenseButton_submit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addExpenseFragment_name_EditText.getText().toString().isEmpty() ||
                        addExpenseFragment_amount_EditText.getText().toString().isEmpty() ||
                        addExpenseFragment_date_TextView.getText().toString().equals(getResources().getString(R.string.none)) ||
                        addExpenseFragment_cat_TextView.getText().toString().equals(getResources().getString(R.string.none))) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_entries),
                            Toast.LENGTH_SHORT).show();
                } else {
                    expense.setName(addExpenseFragment_name_EditText.getText().toString());
                    expense.setAmount(Double.parseDouble(addExpenseFragment_amount_EditText.getText().toString()));
                    expense.setCategory(addExpenseFragment_cat_TextView.getText().toString());
                    mListener.addExpense(expense);
                    mListener.setCurrentFragment(0);
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddExpenseFragment.IAddExpenseFragmentListener) {
            mListener = (AddExpenseFragment.IAddExpenseFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    IAddExpenseFragmentListener mListener;
    interface IAddExpenseFragmentListener {
        void setCurrentFragment(int fragment);
        void addExpense(Expense expense);
    }
}