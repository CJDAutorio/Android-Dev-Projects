package edu.uncc.itcs4180.homework02;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Task> taskArrayList = new ArrayList<>();
    private TextView mainTaskCountLabel;
    private TextView mainTaskUpcomingNameLabel;
    private TextView mainTaskUpcomingDateLabel;
    private TextView mainTaskUpcomingPriLabel;
    private Button mainViewTaskButton;
    private Button mainCreateTaskButton;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize globals
        mainTaskCountLabel = findViewById(R.id.mainTaskCountLabel);
        mainTaskUpcomingNameLabel = findViewById(R.id.mainTaskUpcomingNameLabel);
        mainTaskUpcomingDateLabel = findViewById(R.id.mainTaskUpcomingDateLabel);
        mainTaskUpcomingPriLabel = findViewById(R.id.mainTaskUpcomingPriLabel);
        mainViewTaskButton = findViewById(R.id.mainViewTaskButton);
        mainCreateTaskButton = findViewById(R.id.mainCreateTaskButton);

        setUpcomingTask();

        ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result != null && result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null && result.getData().getSerializableExtra(
                            CreateTaskActivity.TASK_KEY) != null) {
                        taskArrayList.add((Task) result.getData().getSerializableExtra(
                                CreateTaskActivity.TASK_KEY));
                        Log.d(TAG, "Task added to taskArrayList: " + ((Task) result.getData()
                                .getSerializableExtra(CreateTaskActivity.TASK_KEY)).getName());
                    }
                }
            }
        });

        // When Create button is pressed
        mainCreateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);

                startForResult.launch(intent);
            }
        });
    }

    private void setUpcomingTask() {
        // Set taskcount label to arraylist size
        String mainTaskCountLabelString = getResources()
                .getString(R.string.main_taskcount_label, taskArrayList.size());
        mainTaskCountLabel.setText(mainTaskCountLabelString);

        // Set upcoming task list
        if (taskArrayList.isEmpty()) {
            mainTaskUpcomingNameLabel.setText("");
            mainTaskUpcomingDateLabel.setText("");
            mainTaskUpcomingPriLabel.setText("");
        } else {
            Collections.sort(taskArrayList);

            mainTaskUpcomingNameLabel.setText(taskArrayList.get(0).getName());
            mainTaskUpcomingDateLabel.setText(taskArrayList.get(0).dateToString());
            mainTaskUpcomingPriLabel.setText(taskArrayList.get(0).priToString());
        }
    }
}