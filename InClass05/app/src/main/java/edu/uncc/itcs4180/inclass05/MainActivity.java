package edu.uncc.itcs4180.inclass05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

/**
 * Assignment #: InClass05
 * FileName: MainActivity.java
 * CJ D'Autorio, Mason Pipkin
 */
public class MainActivity extends AppCompatActivity implements AppCategoriesFragment.IListener,
        AppsListFragment.IListener {
    private final static String TAG = "inclass05-MainActivity";
    private int currentFragment = 0;
    private DataServices.App currentApp;
    private String currentCat = "";

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
                currentFragment = fragment;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, AppCategoriesFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                Log.d(TAG, "setCurrentFragment: Fragment Index:" + currentFragment);
                break;
            case 1:
                currentFragment = fragment;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, AppsListFragment.newInstance(currentCat))
                        .addToBackStack(null)
                        .commit();
                Log.d(TAG, "setCurrentFragment: Fragment Index:" + currentFragment);
                break;
            case 2:
                currentFragment = fragment;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, AppDetailsFragment.newInstance(currentApp))
                        .addToBackStack(null)
                        .commit();
                Log.d(TAG, "setCurrentFragment: Fragment Index:" + currentFragment);
                break;
            default:
                Log.e(TAG, "setCurrentFragment: Error selecting fragment!");
        }
    }

    @Override
    public void setCurrentApp(DataServices.App app) {
        currentApp = app;
    }

    @Override
    public void setCurrentCategory(String category) {
        currentCat = category;
    }
}