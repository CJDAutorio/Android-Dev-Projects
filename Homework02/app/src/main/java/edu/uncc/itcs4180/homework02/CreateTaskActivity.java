package edu.uncc.itcs4180.homework02;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class CreateTaskActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener {
    EditText createNameEditText;
    Button createDateButton;
    TextView createDateText;
    RadioGroup createPriRadioGroup;
    RadioButton createPriRadioButtonHigh;
    RadioButton createPriRadioButtonMed;
    RadioButton createPriRadioButtonLow;
    Button createCancelButton;
    Button createSubmitButton;
    Task task = new Task();
    public static String TASK_KEY = "TASK";
    private final String TAG = "CreateTaskActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        // Initialize globals
        createNameEditText = findViewById(R.id.createNameEditText);
        createDateButton = findViewById(R.id.createDateButton);
        createDateText = findViewById(R.id.createDateText);
        createPriRadioGroup = findViewById(R.id.createPriRadioGroup);
        createPriRadioButtonHigh = findViewById(R.id.createPriRadioButtonHigh);
        createPriRadioButtonMed = findViewById(R.id.createPriRadioButtonMed);
        createPriRadioButtonLow = findViewById(R.id.createPriRadioButtonLow);
        createCancelButton = findViewById(R.id.createCancelButton);
        createSubmitButton = findViewById(R.id.createSubmitButton);

        // On createNameEditText change
        createNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (createNameEditText.getText().length() > 0) {
                    task.setName(createNameEditText.getText().toString());
                    Log.d(TAG, "Current Task Name: " + task.getName());
                }
            }

        });


        // On createDateButton press
        createDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context)
            }
        });
    }
}