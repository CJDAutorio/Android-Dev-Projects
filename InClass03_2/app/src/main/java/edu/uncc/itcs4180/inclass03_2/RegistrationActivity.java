package edu.uncc.itcs4180.inclass03_2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    User userProfile = new User();
    private String username;
    private String email;
    private int id;
    private String dept;
    final static public String USER_KEY = "USER";
    EditText nameEditText;
    EditText emailEditText;
    EditText idEditText;
    Button selectDeptButton;
    TextView selectDeptText;
    Button submitButton;

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result != null && result.getResultCode() == RESULT_OK) {
                if (result.getData() != null && result.getData().getStringExtra(SelectDepartmentActivity.DEPT_KEY) != null) {
                    selectDeptText.setText(result.getData().getStringExtra(SelectDepartmentActivity.DEPT_KEY));
                    userProfile.setDept(result.getData().getStringExtra(SelectDepartmentActivity.DEPT_KEY));
                    Log.d("RegistrationActivity", "userProfileDept = " + userProfile.getDept());
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize globals
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        idEditText = findViewById(R.id.idEditText);
        selectDeptButton = findViewById(R.id.deptButton);
        selectDeptText = findViewById(R.id.deptText);
        submitButton = findViewById(R.id.submitButtonReg);

        // Initialize text values
        if (userProfile.getUsername().length() > 0) {
            nameEditText.setText(userProfile.getUsername());
        }
        if (userProfile.getEmail().length() > 0) {
            nameEditText.setText(userProfile.getEmail());
        }
        if (userProfile.getId() > -1) {
            idEditText.setText(userProfile.getId());
        }

        // When text is submitted to name
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (nameEditText.getText().length() == 0) {
                    Toast.makeText(RegistrationActivity.this, "Please enter a name",
                            Toast.LENGTH_SHORT).show();
                } else {
                    userProfile.setUsername(nameEditText.getText().toString());
                    Log.d("RegistrationActivity", "userProfileName = " + userProfile.getUsername());
                }
            }
        });

        // When text is submitted to email
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (emailEditText.getText().length() == 0 || !emailEditText.getText().toString()
                        .contains("@")) {
                    Toast.makeText(RegistrationActivity.this,
                            "Please enter a valid email", Toast.LENGTH_SHORT).show();
                } else {
                    userProfile.setEmail(emailEditText.getText().toString());
                    Log.d("RegistrationActivity", "userProfileEmail = " + userProfile.getEmail());
                }
            }
        });

        // When number is submitted to id
        idEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (idEditText.getText().toString().length() == 0) {
                    Toast.makeText(RegistrationActivity.this,
                            "Please enter a valid ID", Toast.LENGTH_SHORT).show();
                } else {
                    userProfile.setId(Integer.parseInt(idEditText.getText().toString()));
                    Log.d("RegistrationActivity", "userProfileID = " + userProfile.getId());
                }
            }
        });

        // When Select dept button is clicked
        selectDeptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, SelectDepartmentActivity.class);
                startForResult.launch(intent);
            }
        });

        // When Submit button is clicked
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userProfile.getUsername() != null && userProfile.getUsername().length() > 0 &&
                        userProfile.getDept() != null && userProfile.getDept().length() > 0 &&
                        userProfile.getEmail() != null && userProfile.getEmail().length() > 0 &&
                        userProfile.getId() > -1) {
                    Intent intent = new Intent(RegistrationActivity.this, Profile.class);
                    intent.putExtra(USER_KEY, userProfile);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegistrationActivity.this,
                            "Please check inputs and try again.", Toast.LENGTH_SHORT).show();
                    Log.d("RegistrationActivity", "userProfileDept = " + userProfile.getDept());
                    Log.d("RegistrationActivity", "userProfileName = " + userProfile.getUsername());
                    Log.d("RegistrationActivity", "userProfileEmail = " + userProfile.getEmail());
                    Log.d("RegistrationActivity", "userProfileID = " + userProfile.getId());
                }
            }
        });
    }
}