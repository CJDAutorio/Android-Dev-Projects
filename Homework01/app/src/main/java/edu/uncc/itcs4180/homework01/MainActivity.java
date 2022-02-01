package edu.uncc.itcs4180.homework01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Global variables
    private float billValue = 0.0f;
    private float tipValue = 0.1f;
    private float customTipValueNumber = 0.4f;
    private int splitBy = 0;
    private float billTotal = 0.0f;
    private float billPerPersonTotal = 0.0f;
    private boolean useCustomTip = false;
    EditText billValueEditText;
    RadioGroup tipPercentRadioGroup;
    SeekBar customTipSeekBar;
    RadioGroup splitRadioGroup;
    Button clearButton;
    TextView customTipValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize global components
        billValueEditText = findViewById(R.id.billTotalNumber);
        tipPercentRadioGroup = findViewById(R.id.tipPercentRadioGroup);
        customTipSeekBar = findViewById(R.id.customTipSeekBar);
        splitRadioGroup = findViewById(R.id.splitRadioGroup);
        clearButton = findViewById(R.id.clearButton);
        customTipValueTextView = findViewById(R.id.customTipValue);

        // When billValueEditText changes
        billValueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (billValueEditText.getText().length() > 0) {
                    setBillValue();
                    setTipValue();
                    updateTipText();
                    updateTotalText();
                    updatePersonTotalText();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // When tipPercentRadioGroup changes
        tipPercentRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                setBillValue();
                setTipValue();
                updateTipText();
                updateTotalText();
                updatePersonTotalText();
            }
        });

        // When customTipSeekBar changes
        customTipSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateCustomTip();
                setTipValue();
                if (useCustomTip) {
                    setBillValue();
                    updateTipText();
                    updateTotalText();
                    updatePersonTotalText();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // When splitRadioGroup changes
        splitRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                setBillValue();
                setTipValue();
                updateTipText();
                updateTotalText();
                updatePersonTotalText();
            }
        });

        // When clearButton is pressed
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDefault();
            }
        });
    }

    void setBillValue() {
        // Set billValue global to billValueEditText value
        if (billValueEditText.getText().length() > 0) {
            billValue = Float.parseFloat(billValueEditText.getText().toString());
            Log.d("Tip Calculator", "billValue: " + billValue);
        }
    }

    void setTipValue() {
        // Set tipValue global according to tipPercentRadioGroup value
        if (tipPercentRadioGroup.getCheckedRadioButtonId() == R.id.tipRadio10) {
            tipValue = 0.1f;
        } else if (tipPercentRadioGroup.getCheckedRadioButtonId() == R.id.tipRadio15) {
            tipValue = 0.15f;
        } else if (tipPercentRadioGroup.getCheckedRadioButtonId() == R.id.tipRadio18) {
            tipValue = 0.18f;
        } else if (tipPercentRadioGroup.getCheckedRadioButtonId() == R.id.tipRadioCustom) {
            useCustomTip = true;
            tipValue = customTipValueNumber;
        }
        Log.d("Tip Calculator", "tipValue: " + tipValue);
    }

    void updateCustomTip() {
        String customTipValueText = new StringBuilder().append(
                customTipSeekBar.getProgress()).append("%").toString();
        customTipValueTextView.setText(customTipValueText);
        customTipValueNumber = (float) (customTipSeekBar.getProgress() / 100.0);
        Log.d("Tip Calculator", "Custom tipValue: " + customTipValueNumber);
    }

    float calculateTip() {
        return billValue * tipValue;
    }

    void updateTipText() {
        TextView tipResultNumber = findViewById(R.id.tipResultNumber);
        String tipResultText = new StringBuilder().append("$").append(Float.toString(
                calculateTip())).toString();
        tipResultNumber.setText(tipResultText);
    }

    void updateTotalText() {
        TextView totalResultNumber = findViewById(R.id.totalResultNumber);
        String totalResultText = new StringBuilder().append("$").append(Float.toString(
                billValue + calculateTip())).toString();
        totalResultNumber.setText(totalResultText);
    }

    void updatePersonTotalText() {
        TextView totalPersonNumber = findViewById(R.id.totalPersonNumber);
        if (splitRadioGroup.getCheckedRadioButtonId() == R.id.splitOne) {
            splitBy = 1;
        } else if (splitRadioGroup.getCheckedRadioButtonId() == R.id.splitTwo) {
            splitBy = 2;
        } else if (splitRadioGroup.getCheckedRadioButtonId() == R.id.splitThree) {
            splitBy = 3;
        } else if (splitRadioGroup.getCheckedRadioButtonId() == R.id.splitFour) {
            splitBy = 4;
        }
        String totalPersonText = new StringBuilder().append("$").append(Float.toString(
                (billValue + calculateTip()) / splitBy)).toString();
        totalPersonNumber.setText(totalPersonText);
    }

    void setDefault() {
        billValue = 0.0f;
        tipValue = 0.1f;
        customTipValueNumber = 0.4f;
        splitBy = 0;
        billTotal = 0.0f;
        billPerPersonTotal = 0.0f;
        useCustomTip = false;
        customTipValueTextView.setText(40 + "%");
        billValueEditText.setText("");
        billValueEditText.setHint("Enter Bill Total");
        customTipSeekBar.setProgress(40);

        tipPercentRadioGroup.check(R.id.tipRadio10);
        splitRadioGroup.check(R.id.splitOne);

        updateTipText();
        updateTotalText();
        updatePersonTotalText();
    }
}