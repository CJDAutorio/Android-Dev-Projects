package edu.uncc.itcs4180.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "note.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        db.noteDao().insertAll(new Note("Subject 1", "Note 1"),
                new Note("Subject 2", "Note 2"),
                new Note("Subject 3", "Note 3"));
        Log.d(TAG, "onCreate: " + db.noteDao().getAll());

        Note note = db.noteDao().findById(1);
        note.subject = "Updated Subject 1";
        note.note = "Testing note update";
        db.noteDao().update(note);
        Log.d(TAG, "onCreate: " + note);

        Log.d(TAG, "onCreate: " + db.noteDao().getAll());

        db.noteDao().delete(note);

        Log.d(TAG, "onCreate: " + db.noteDao().getAll());
    }
}