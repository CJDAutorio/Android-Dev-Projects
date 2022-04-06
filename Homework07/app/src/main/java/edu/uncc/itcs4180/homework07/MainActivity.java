package edu.uncc.itcs4180.homework07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "homework07-MainActivity";
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            Log.d(TAG, "Current user: null");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainerView, new LoginFragment())
                    .commit();
        } else {
            Log.d(TAG, "Current user: " + mAuth.getCurrentUser().getUid());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainerView, new CartListFragment())
                    .commit();
        }
    }
}