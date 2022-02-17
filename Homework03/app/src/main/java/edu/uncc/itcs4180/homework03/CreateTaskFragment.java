package edu.uncc.itcs4180.homework03;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateTaskFragment extends Fragment {
    ArrayList<Task> taskArrayList;
    Task currentTask = new Task();
    EditText create_name_editText;
    TextView create_date_text;
    Button create_date_button;
    RadioGroup create_pri_radioGroup;
    Button create_cancel_button;
    Button create_submit_button;
    private int day, month, year, priority;

    final static String ARG_TASKLIST = "ARG_TASKLIST";
    final static String TAG = "CreateTaskFragment";

    public CreateTaskFragment() {
        // Required empty public constructor
    }

    public static CreateTaskFragment newInstance(ArrayList<Task> taskArrayList) {
        CreateTaskFragment fragment = new CreateTaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASKLIST, taskArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskArrayList = (ArrayList<Task>) getArguments().getSerializable(ARG_TASKLIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_task, container, false);

        // Initialize globals
        create_name_editText = view.findViewById(R.id.create_name_editText);
        create_date_text = view.findViewById(R.id.create_date_text);
        create_date_button = view.findViewById(R.id.create_date_button);
        create_pri_radioGroup = view.findViewById(R.id.create_pri_radioGroup);
        create_cancel_button = view.findViewById(R.id.create_cancel_button);
        create_submit_button = view.findViewById(R.id.create_submit_button);
        create_date_text.setText("");

        // On create_date_button press
        create_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        currentTask.setDate(i1, i2 + 1, i);
                        create_date_text.setText(currentTask.getDate());
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        // On create_submit_button press
        create_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (create_name_editText.getText().toString().isEmpty() ||
                        create_date_text.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_entries),
                            Toast.LENGTH_SHORT).show();
                } else {
                    currentTask.setName(create_name_editText.getText().toString());
                    setPriority();
                    mListener.addTask(currentTask);
                    mListener.setCurrentFragment(0);
                }
            }
        });

        // On create_cancel_button press
        create_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.setCurrentFragment(0);
            }
        });

        return view;
    }

    public void setPriority() {
        if (create_pri_radioGroup.getCheckedRadioButtonId() == R.id.create_pri_high) {
            currentTask.setPriority(0);
        } else if (create_pri_radioGroup.getCheckedRadioButtonId() == R.id.create_pri_med) {
            currentTask.setPriority(1);
        } else if (create_pri_radioGroup.getCheckedRadioButtonId() == R.id.create_pri_low) {
            currentTask.setPriority(2);
        } else {
            Log.e(TAG, "setPriority: Error setting priority!");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof CreateTaskFragment.IListener) {
            mListener = (CreateTaskFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    CreateTaskFragment.IListener mListener;
    public interface IListener {
        void setCurrentFragment(int fragment);
        void addTask(Task task);
    }
}