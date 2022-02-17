package edu.uncc.itcs4180.threadhandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                //Log.d("demo", "Message Received ... " + message.what);
                Log.d("demo", "Message Received ... " + message.obj);
                switch (message.what) {
                    case DoWork.STATUS_START:
                        Log.d("demo", "Starting ...");
                        break;
                    case DoWork.STATUS_STOP:
                        Log.d("demo", "Stopping ...");
                        break;
                    case DoWork.STATUS_PROGRESS:
                        Log.d("demo", "Progress ... " + message.obj);
                        break;
                }

                return false;
            }
        });

        new Thread(new DoWork()).start();
    }

    class DoWork implements Runnable {
        static final int STATUS_START = 0x00;
        static final int STATUS_PROGRESS = 0x01;
        static final int STATUS_STOP = 0x02;

        @Override
        public void run() {
            Log.d("demo", "Started Work");
            Message startMessage = new Message();
            startMessage.what = STATUS_START;
            handler.sendMessage(startMessage);
            // Busy sample work for tutorial
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 1000000; j++) {
                   Message message = new Message();
                   message.what = STATUS_PROGRESS;
                   message.obj = (Integer) i;

                    handler.sendMessage(message);
                }
            }

            Message stopMessage = new Message();
            stopMessage.what = STATUS_STOP;
            handler.sendMessage(stopMessage);
        }
    }
}