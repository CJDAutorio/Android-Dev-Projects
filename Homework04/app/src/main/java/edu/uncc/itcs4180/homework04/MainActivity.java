package edu.uncc.itcs4180.homework04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements UsersFragment.IListener,
        FilterByStateFragment.IListener, SortFragment.IListener {
    private final static String TAG = "homework04-MainActivity";
    private int currentFragment = 0;
    private String currentState = "";
    private String currentSortType = "";
    private boolean isAscending = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCurrentFragment(currentFragment);
    }

    @Override
    public void setCurrentFragment(int fragment) {
        switch (fragment) {
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, UsersFragment.newInstance(currentState))
                        .addToBackStack(null)
                        .commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, FilterByStateFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, SortFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                Log.e(TAG, "setCurrentFragment: Error selecting fragment!");
        }
    }

    @Override
    public void setCurrentSort(String sortType, boolean isAscending) {
        currentSortType = sortType;
        this.isAscending = isAscending;
        Log.d(TAG, "currentSortType" + currentSortType);
        Log.d(TAG, "isAscending" + isAscending);
    }

    @Override
    public void setCurrentState(String state) {
        currentState = state;
    }
}