package edu.uncc.itcs4180.threads_threadpool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    Button button;
    ExecutorService threadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        threadPool = Executors.newFixedThreadPool(4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Thread thread = new Thread(new DoWork(), "Worker 1");
//                thread.start();
                threadPool.execute(new DoWork());
            }
        });
    }

    class DoWork implements Runnable {
        @Override
        public void run() {
            Log.d("demo", "Started Work");
            // Busy sample work for tutorial
            for (int i = 0; i < 10000; i++) {
                for (int j = 0; j < 10000; j++) {
                }
            }
            Log.d("demo", "Ended Work");
        }
    }
}