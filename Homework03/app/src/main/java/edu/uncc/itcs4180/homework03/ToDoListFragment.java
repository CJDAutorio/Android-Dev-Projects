package edu.uncc.itcs4180.homework03;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoListFragment extends Fragment {
    TextView todo_taskCount_textView;
    TextView todo_taskName_textView;
    TextView todo_taskDate_textView;
    TextView todo_taskPri_textView;
    Button todo_viewTasks_button;
    Button todo_createTask_button;
    ArrayList<Task> taskArrayList = new ArrayList<Task>();

    final static String ARG_TASKLIST = "ARG_TASKLIST";
    final static String TAG = "ToDoListFragment";

    public ToDoListFragment() {
        // Required empty public constructor
    }
    public static ToDoListFragment newInstance(ArrayList<Task> taskArrayList) {
        ToDoListFragment fragment = new ToDoListFragment();
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
        View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);

        // Initialize globals
        todo_taskCount_textView = view.findViewById(R.id.todo_taskCount_textView);
        todo_taskName_textView = view.findViewById(R.id.todo_taskName_textView);
        todo_taskDate_textView = view.findViewById(R.id.todo_taskDate_textView);
        todo_taskPri_textView = view.findViewById(R.id.todo_taskPri_textView);
        todo_viewTasks_button = view.findViewById(R.id.todo_viewTasks_button);
        todo_createTask_button = view.findViewById(R.id.todo_createTask_button);

        // Updates current task
        updateCurrentTask();

        // On todo_viewTasks_button press
        todo_viewTasks_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] taskListNameArray = updateTaskListNameArray();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select Task")
//                        .setSingleChoiceItems(taskListNameArray, 0,
//                                new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                mListener.setCurrentTask(i);
//                                mListener.setCurrentFragment(2);
//                            }
//                        })
                        .setItems(taskListNameArray, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mListener.setCurrentTask(i);
                                mListener.setCurrentFragment(2);
                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.cancel)
                                , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d(TAG, "onClick: Cancel");
                            }
                        });

                builder.create().show();
            }
        });

        // On todo_createTask_button press
        todo_createTask_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.setCurrentFragment(1);
            }
        });

        return view;
    }

    public CharSequence[] updateTaskListNameArray() {
        CharSequence[] taskListNameArray = new CharSequence[taskArrayList.size()];

        for (int i = 0; i < taskArrayList.size(); i++) {
            taskListNameArray[i] = taskArrayList.get(i).getName();
        }

        return taskListNameArray;
    }

    public void updateCurrentTask() {
        // Set task count
        todo_taskCount_textView.setText(getResources().getString(
                R.string.taskcount, taskArrayList.size()));

        // Set upcoming task
        if (!taskArrayList.isEmpty()) {
            todo_taskName_textView.setText(taskArrayList.get(0).getName());
            todo_taskDate_textView.setText(taskArrayList.get(0).getDate());
            todo_taskPri_textView.setText(taskArrayList.get(0).getPriority());
        } else {
            todo_taskName_textView.setText("");
            todo_taskDate_textView.setText("");
            todo_taskPri_textView.setText("");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof ToDoListFragment.IListener) {
            mListener = (ToDoListFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    ToDoListFragment.IListener mListener;
    public interface IListener {
        void setCurrentFragment(int fragment);
        void setCurrentTask(int taskIndex);
    }
}