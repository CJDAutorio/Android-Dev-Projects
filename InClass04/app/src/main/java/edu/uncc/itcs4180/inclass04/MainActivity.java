package edu.uncc.itcs4180.inclass04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements LoginFragment.IListener,
        RegisterFragment.IListener, UpdateFragment.IListener, AccountFragment.IListener {
    private final static String TAG = "MainActivity";
    private int currentFragment = 0;

    DataServices.Account currentAccount = new DataServices.Account("", "", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new LoginFragment())
                .commit();
    }

    @Override
    public void setAccount(DataServices.Account account) {
        currentAccount = account;
        Log.d(TAG, "setAccount: name: " + account.getName());
    }

    @Override
    public void setCurrentFragment(int fragment) {
        switch (fragment) {
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, LoginFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, RegisterFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, UpdateFragment.newInstance(currentAccount))
                        .addToBackStack(null)
                        .commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, AccountFragment.newInstance(currentAccount))
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                Log.e(TAG, "setCurrentFragment: Error selecting fragment!");
        }
    }
}