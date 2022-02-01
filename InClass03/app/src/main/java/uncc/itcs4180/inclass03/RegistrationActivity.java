package uncc.itcs4180.inclass03;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {
    Profile userProfile;
    private String username;
    private String email;
    private int id;
    private String dept;
    EditText nameEditText;
    EditText emailEditText;
    EditText idEditText;
    Button selectDeptButton;
    TextView selectDeptText;
    Button submitButton;
    final static int REQ_CODE = 100;

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result != null && result.getResultCode() == RESULT_OK) {
                if (result.getData() != null && result.getData().getStringExtra(SelectDepartmentActivity.KEY_NAME) != null) {
                    selectDeptText.setText(result.getData().getStringExtra(SelectDepartmentActivity.KEY_NAME));
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
                    username = nameEditText.getText().toString();
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
                    email = emailEditText.getText().toString();
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
                    id = Integer.parseInt(idEditText.getText().toString());
                }
            }
        });

        // When Submit button is clicked
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createProfile();
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
    }

    void createProfile() {
        if (nameEditText.getText().toString().length() > 0) {
            if (emailEditText.getText().toString().length() > 0 && emailEditText.getText()
                    .toString().contains("@")) {
                if (idEditText.getText().toString().length() > 0) {
                    if (selectDeptText.getText().toString().length() > 0) {
                        userProfile = new Profile(username, email, id, dept);
                    }
                }
            }
        } else {
            Toast.makeText(RegistrationActivity.this,
                    "Please check input values", Toast.LENGTH_SHORT).show();
        }
    }
}