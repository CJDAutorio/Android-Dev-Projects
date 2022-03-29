package edu.uncc.itcs4180.inclass06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Assignment #: InClass06
 * FileName: MainActivity.java
 * CJ D'Autorio, Mason Pipkin
 */
public class MainActivity extends AppCompatActivity {
    Button generateButton;
    SeekBar seekBar;
    TextView averageText;
    TextView countTimesText;
    TextView progressText;
    ProgressBar progressBar;
    ListView listView;
    Handler handler;
    ArrayAdapter<Double> adapter;
    ExecutorService threadPool;
    private int complexity;
    private static final String TAG = "inclass06-MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize globals
        generateButton = findViewById(R.id.generateButton);
        seekBar = findViewById(R.id.seekBar);
        averageText = findViewById(R.id.averageText);
        countTimesText = findViewById(R.id.countTimesText);
        progressText = findViewById(R.id.progressText);
        progressBar = findViewById(R.id.progressBar);
        listView = findViewById(R.id.listView);
        complexity = seekBar.getProgress();

        // Initialize TextViews
        averageText.setText(getResources().getString(R.string.average,
                0.0));
        countTimesText.setText(getResources().getString(R.string.count_times,
                seekBar.getProgress()));

        // Initialize threadPool
        threadPool = Executors.newFixedThreadPool(2);

        // Initialize handler
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                //Log.d("demo", "Message Received ... " + message.what);
                Log.d(TAG, "Progress: " + message.what);

                if (message.obj != null) {
                    adapter = new ArrayAdapter<Double>(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            android.R.id.text1,
                            (Double[]) message.obj
                    );
                    listView.setAdapter(adapter);
                    progressBar.setProgress(message.what + 1, true);
                    progressText.setText(message.what + 1 + "/" + complexity);
                    averageText.setText(getResources().getString(R.string.average,
                            getAverage((Double[]) message.obj)));

                    if (((Double[]) message.obj).length == complexity) {
                        seekBar.setEnabled(true);
                    }
                }

                return false;
            }
        });

        // On seekBar change
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                complexity = i;
                countTimesText.setText(getResources().getString(R.string.count_times, complexity));
                progressBar.setMax(complexity);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // On generateButton press
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setEnabled(false);
                progressText.setText(0 + "/" + complexity);
                threadPool.execute(new DoHeavyWork(complexity));
            }
        });
    }

    private double getAverage(Double[] numberList) {
        double total = 0;
        for (int i = 0; i < numberList.length; i++) {
            total += numberList[i];
        }

        return total / numberList.length;
    }

    // DoHeavyWork class
    class DoHeavyWork implements Runnable {
        int complexity;
        ArrayList<Double> numberList = new ArrayList<>();

        public DoHeavyWork(int complexity) {
            this.complexity = complexity;
        }

        public Double[] getNumbers() {
            Double[] numberArray = new Double[numberList.size()];
            for (int i = 0; i < numberList.size(); i++) {
                numberArray[i] = numberList.get(i);
            }

            return numberArray;
        }

        @Override
        public void run() {
            Log.d(TAG, "Started DoHeavyWork");

            for (int i = 0; i < complexity; i++) {
                numberList.add(HeavyWork.getNumber());
                Message progressMessage = new Message();
                progressMessage.what = i;
                progressMessage.obj = getNumbers();
                handler.sendMessage(progressMessage);
            }
        }
    }
}