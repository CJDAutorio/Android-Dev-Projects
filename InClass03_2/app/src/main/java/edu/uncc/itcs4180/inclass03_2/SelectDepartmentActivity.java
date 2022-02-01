package edu.uncc.itcs4180.inclass03_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SelectDepartmentActivity extends AppCompatActivity {
    public static String DEPT_KEY = "DEPT";
    String deptName;
    RadioGroup deptRadioGroup;
    Button selectButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_department);

        // Initialize globals
        deptRadioGroup = findViewById(R.id.deptRadioGroup);
        selectButton = findViewById(R.id.selectButton);
        cancelButton = findViewById(R.id.cancelButton);

        // On radio select
        deptRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (deptRadioGroup.getCheckedRadioButtonId() == R.id.radioButtonCS) {
                    deptName = getResources().getString(R.string.string_cs_selectdept);
                } else if (deptRadioGroup.getCheckedRadioButtonId() == R.id.radioButtonSIS) {
                    deptName = getResources().getString(R.string.string_sis_selectdept);
                } else if (deptRadioGroup.getCheckedRadioButtonId() == R.id.radioButtonBio) {
                    deptName = getResources().getString(R.string.string_bio_selectdept);
                } else if (deptRadioGroup.getCheckedRadioButtonId() == R.id.radioButtonDS) {
                    deptName = getResources().getString(R.string.string_ds_selectdept);
                }
            }
        });

        // On Select button
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deptName != null) {
                    Intent intent = new Intent(SelectDepartmentActivity.this, RegistrationActivity.class);
                    intent.putExtra(DEPT_KEY, deptName);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(SelectDepartmentActivity.this,
                            "Please select a department", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // On Cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectDepartmentActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }


}