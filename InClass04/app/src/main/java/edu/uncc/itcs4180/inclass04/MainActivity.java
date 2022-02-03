package edu.uncc.itcs4180.inclass04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements LoginFragment.IListener {
    final static String TAG = "MainActivity";

    DataServices.Account account = new DataServices.Account("", "", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.loginFragmentContainer, new LoginFragment())
                .commit();
    }

    @Override
    public void setAccount(DataServices.Account account) {
        this.account = account;
        Log.d(TAG, "setAccount: name: " + account.getName());
    }
}