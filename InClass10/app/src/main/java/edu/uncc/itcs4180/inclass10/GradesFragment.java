package edu.uncc.itcs4180.inclass10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GradesFragment extends Fragment implements CoursesRecyclerViewAdapter.ICoursesRecycler {
    final String TAG = "inclass10-GradesFragment";
    float gpa = 0.0F;
    int creditHours = 0;
    ArrayList<Course> courseList;
    private final static String ARG_COURSES = "ARG_COURSES";
    TextView grades_gpa_textView;
    TextView grades_hours_textView;
    Button grades_add_button;
    RecyclerView grades_courses_recyclerView;
    LinearLayoutManager coursesLayoutManager;
    CoursesRecyclerViewAdapter coursesRecyclerViewAdapter;

    public GradesFragment() {
        // Required empty public constructor
    }

    public static GradesFragment newInstance(ArrayList<Course> courseList) {
        GradesFragment fragment = new GradesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COURSES, courseList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            courseList = (ArrayList<Course>) getArguments().get(ARG_COURSES);
            Log.d(TAG, "onCreate-courseList: " + courseList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grades, container, false);

        Log.d(TAG, "onCreateView running");
        courseList = mListener.getCourseList();
        Log.d(TAG, "onCreateView-courseList: " + courseList);

        getActivity().setTitle(getResources().getString(R.string.grades));
//        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setCustomView(R.menu.menu_add);

        grades_gpa_textView = view.findViewById(R.id.grades_gpa_textView);
        grades_hours_textView = view.findViewById(R.id.grades_hours_textView);
        grades_add_button = view.findViewById(R.id.grades_add_button);
        grades_courses_recyclerView = view.findViewById(R.id.grades_courses_recyclerView);

        calculateGpa();
        grades_gpa_textView.setText(getResources().getString(R.string.gpa_num, gpa));
        grades_hours_textView.setText(getResources().getString(R.string.hours_num, creditHours));

        populateList();

        // On grades_add_button press
        grades_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, AddFragment.newInstance())
                        .commit();
            }
        });

        return view;
    }

    private void populateList() {
        Log.d(TAG, "populateList running");
        Log.d(TAG, "populateList-courseList: " + courseList);

        coursesLayoutManager = new LinearLayoutManager(getContext());
        coursesRecyclerViewAdapter = new CoursesRecyclerViewAdapter(courseList, this);
        grades_courses_recyclerView.setAdapter(coursesRecyclerViewAdapter);
        grades_courses_recyclerView.setLayoutManager(coursesLayoutManager);
        coursesRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void calculateGpa() {
        if (!courseList.isEmpty()) {
            float gradePoints = 0;
            for (int i = 0; i < courseList.size(); i++) {
                gradePoints += courseList.get(i).grade * courseList.get(i).creditHours;
                creditHours += courseList.get(i).creditHours;
            }

            gpa = gradePoints / creditHours;
        } else {
            gpa = 0.0F;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof GradesFragment.IListener) {
            mListener = (GradesFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    @Override
    public void deleteCourse(Course course) {
        mListener.deleteCourse(course);
        populateList();
    }

    GradesFragment.IListener mListener;
    public interface IListener {
        void deleteCourse(Course course);
        ArrayList<Course> getCourseList();
    }
}