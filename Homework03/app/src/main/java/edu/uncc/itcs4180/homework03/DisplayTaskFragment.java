package edu.uncc.itcs4180.homework03;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DisplayTaskFragment extends Fragment {
    Task currentTask;
    TextView display_taskname_text;
    TextView display_taskdate_text;
    TextView display_taskpri_text;
    Button display_cancel_button;
    Button display_delete_button;

    final static String ARG_TASK = "ARG_TASK";
    final static String TAG = "DisplayTaskFragment";

    public DisplayTaskFragment() {
        // Required empty public constructor
    }

    public static DisplayTaskFragment newInstance(Task currentTask) {
        DisplayTaskFragment fragment = new DisplayTaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK, currentTask);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentTask = (Task) getArguments().getSerializable(ARG_TASK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_task, container, false);

        // Initialize globals
        display_taskname_text = view.findViewById(R.id.display_taskname_text);
        display_taskdate_text = view.findViewById(R.id.display_taskdate_text);
        display_taskpri_text = view.findViewById(R.id.display_taskpri_text);
        display_cancel_button = view.findViewById(R.id.display_cancel_button);
        display_delete_button = view.findViewById(R.id.display_delete_button);

        display_taskname_text.setText(currentTask.getName());
        display_taskdate_text.setText(currentTask.getDate());
        display_taskpri_text.setText(currentTask.getPriority());

        display_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.setCurrentFragment(0);
            }
        });

        display_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.deleteTask(currentTask);
                mListener.setCurrentFragment(0);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof DisplayTaskFragment.IListener) {
            mListener = (DisplayTaskFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    DisplayTaskFragment.IListener mListener;
    public interface IListener {
        void setCurrentFragment(int fragment);
        void deleteTask(Task task);
    }
}