package edu.uncc.itcs4180.homework03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements ToDoListFragment.IListener,
        CreateTaskFragment.IListener, DisplayTaskFragment.IListener {
    ArrayList<Task> taskArrayList = new ArrayList<Task>();
    private int currentFragment = 0;
    private int taskIndex = 0;
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragmentContainerView, new ToDoListFragment())
//                .commit();
        setCurrentFragment(currentFragment);
    }

    @Override
    public void setCurrentFragment(int fragment) {
        switch (fragment) {
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, ToDoListFragment.newInstance(
                                taskArrayList))
                        .addToBackStack(null)
                        .commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, CreateTaskFragment.newInstance(
                                taskArrayList))
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, DisplayTaskFragment.newInstance(
                                taskArrayList.get(taskIndex)))
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                Log.e(TAG, "setCurrentFragment: Error selecting fragment!");
        }
    }

    @Override
    public void deleteTask(Task task) {
        taskArrayList.remove(task);
    }

    @Override
    public void setCurrentTask(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void addTask(Task task) {
        taskArrayList.add(task);
        Collections.sort(taskArrayList);
    }
}