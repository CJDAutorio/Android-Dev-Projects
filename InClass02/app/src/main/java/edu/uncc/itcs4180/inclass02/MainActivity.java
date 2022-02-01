package edu.uncc.itcs4180.inclass02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private double ticketPrice;
    private double discountRate;
    RadioGroup discountRadioGroup;
    EditText editTicketPrice;
    TextView discountedPriceResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On calculateButton click
        findViewById(R.id.calculateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("InClass02", "onClick: calculateButton");
                setDiscountPriceLabel();
            }
        });
        // On clearButton click
        findViewById(R.id.clearButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discountRadioGroup = findViewById(R.id.discountRadioGroup);

                Log.d("InClass02", "onClick: clearButton");
                // Reset globals
                ticketPrice = 0.0;
                discountRate = 0.0;

                // Reset radio group
                discountRadioGroup.setId(R.id.discountRadio5);

                // Reset editTicketPrice text
                editTicketPrice = findViewById(R.id.editTicketPrice);
                editTicketPrice.setText("");

                // Reset discountedPriceResult text
                discountedPriceResult = findViewById(R.id.discountedPriceResult);
                discountedPriceResult.setText("");
            }
        });
    }

    public void checkTicketPrice() {
        // Get ticket price
        editTicketPrice = findViewById(R.id.editTicketPrice);

        // Set ticketPrice global to value in editTicketPrice
        ticketPrice = Double.parseDouble(editTicketPrice.getText().toString());
    }

    public void checkRadioGroupStatus() {
        discountRadioGroup = findViewById(R.id.discountRadioGroup);

        // Check radio group selection and set discountRate global to appropriate value
        int checkedId = discountRadioGroup.getCheckedRadioButtonId();
        if (checkedId == R.id.discountRadio5) {
            discountRate = 0.05;
        } else if (checkedId == R.id.discountRadio10) {
            discountRate = 0.1;
        } else if (checkedId == R.id.discountRadio15) {
            discountRate = 0.15;
        } else if (checkedId == R.id.discountRadio20) {
            discountRate = 0.2;
        } else if (checkedId == R.id.discountRadio50) {
            discountRate = 0.5;
        }

        Log.d("InClass02", "discountRate: " + discountRate);
    }

    public double calculatePrice() {
        // Get discount multiplier value
        double discountMultiplier = 1 - discountRate;

        Log.d("InClass02", "discountMultiplier: " + discountMultiplier);

        // Return total with discount rounded to 2 decimal places
        return Math.round((ticketPrice * discountMultiplier) * 100.0) / 100.0;
        //return ticketPrice * discountMultiplier;
    }

    public void setDiscountPriceLabel() {
        // Get discountedPriceResult TextView
        discountedPriceResult = findViewById(R.id.discountedPriceResult);

        // Check values from price and discount inputs
        checkTicketPrice();
        checkRadioGroupStatus();

        // Set discountedPriceResult text to calculatedPrice result
        discountedPriceResult.setText(String.format("%.2f", calculatePrice()));
    }
}