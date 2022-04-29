package edu.uncc.itcs4180.inclass10;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GradesFragment.IListener,
        AddFragment.IListener {
    final String TAG = "inclass10-MainActivity";
    AppDatabase db;
    ArrayList<Course> courseArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(this, AppDatabase.class, "courses")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        if (((ArrayList<Course>) db.courseDao().getAll()).isEmpty()) {
            courseArrayList = new ArrayList<>();
        } else {
            getCourseList();
        }
        Log.d(TAG, "courseArrayList: " + courseArrayList);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, GradesFragment.newInstance(courseArrayList))
                .commit();
    }

    @Override
    public void deleteCourse(Course course) {
        db.courseDao().delete(course);
        courseArrayList = getCourseList();
        Log.d(TAG, "deleteCourse: " + courseArrayList);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, GradesFragment.newInstance(courseArrayList))
                .commit();
    }

    @Override
    public void addCourse(Course course) {
        db.courseDao().insertAll(course);
        courseArrayList = getCourseList();
    }

    @Override
    public ArrayList<Course> getCourseList() {
        return (ArrayList<Course>) db.courseDao().getAll();
    }
}