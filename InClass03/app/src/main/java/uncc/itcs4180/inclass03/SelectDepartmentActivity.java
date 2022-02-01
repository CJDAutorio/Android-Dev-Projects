package uncc.itcs4180.inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SelectDepartmentActivity extends AppCompatActivity {
    public static String KEY_NAME;
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
                    KEY_NAME = getResources().getString(R.string.string_cs_selectdept);
                } else if (deptRadioGroup.getCheckedRadioButtonId() == R.id.radioButtonSIS) {
                    KEY_NAME = getResources().getString(R.string.string_sis_selectdept);
                } else if (deptRadioGroup.getCheckedRadioButtonId() == R.id.radioButtonBio) {
                    KEY_NAME = getResources().getString(R.string.string_bio_selectdept);
                } else if (deptRadioGroup.getCheckedRadioButtonId() == R.id.radioButtonDS) {
                    KEY_NAME = getResources().getString(R.string.string_ds_selectdept);
                }
            }
        });

        // On Select button
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (KEY_NAME != null) {
                    Intent intent = new Intent(SelectDepartmentActivity.this, RegistrationActivity.class);
                    startActivity(intent);
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