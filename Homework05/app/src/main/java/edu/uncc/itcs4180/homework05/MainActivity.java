package edu.uncc.itcs4180.homework05;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.OkHttpClient;

/**
 * Homework 05
 * MainActivity.java
 * CJ D'Autorio
 */
public class MainActivity extends AppCompatActivity implements LoginFragment.IListener,
        RegisterFragment.IListener, PostsListFragment.IListener, CreatePostFragment.IListener {
    final String TAG = "homework05-MainActivity";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLoggedIn();
    }

    private void isLoggedIn() {
//        sharedPreferences = this.getSharedPreferences(
//                getString(R.string.auth_token), Context.MODE_PRIVATE);

        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);

        if (sharedPreferences.getString(getString(R.string.auth_token),"").isEmpty()) {
            Log.d(TAG, "onCreate: sharedPref auth_token is empty.");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, LoginFragment.newInstance())
                    .commit();
        } else {
            Log.d(TAG, "onCreate: sharedPref auth_token contains key.");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, PostsListFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void setPostsListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, PostsListFragment.newInstance())
                .commit();
    }

    @Override
    public void setLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LoginFragment.newInstance())
                .commit();
    }

    @Override
    public void setCreatePostFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, CreatePostFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void setRegisterFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, RegisterFragment.newInstance())
                .commit();
    }

    @Override
    public void popBackStack() {
        getSupportFragmentManager().popBackStack();
    }
}