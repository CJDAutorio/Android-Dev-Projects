package edu.uncc.itcs4180.inclass03_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    TextView nameTextView;
    TextView emailTextView;
    TextView idTextView;
    TextView deptTextView;
    private User userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize globals
        nameTextView = findViewById(R.id.nameTextProfile);
        emailTextView = findViewById(R.id.emailTextProfile);
        idTextView = findViewById(R.id.idTextProfile);
        deptTextView = findViewById(R.id.deptTextProfile);

        // Get user object
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(RegistrationActivity.USER_KEY)) {
            userProfile = (User) (getIntent().getSerializableExtra(RegistrationActivity.USER_KEY));
        }

        // Set TextView texts
        nameTextView.setText(userProfile.getUsername());
        emailTextView.setText(userProfile.getEmail());
        idTextView.setText(userProfile.getId() + "");
        deptTextView.setText(userProfile.getDept());
    }
}