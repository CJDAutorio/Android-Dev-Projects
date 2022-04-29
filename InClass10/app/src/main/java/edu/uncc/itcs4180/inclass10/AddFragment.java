package edu.uncc.itcs4180.inclass10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class AddFragment extends Fragment {
    final String TAG = "inclass10-AddFragment";
    EditText add_courseNumber_editText;
    EditText add_courseName_editText;
    EditText add_creditHours_editText;
    RadioGroup add_grade_radioGroup;
    Button add_submit_button;
    Button add_cancel_button;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        add_courseNumber_editText = view.findViewById(R.id.add_courseNumber_editText);
        add_courseName_editText = view.findViewById(R.id.add_courseName_editText);
        add_creditHours_editText = view.findViewById(R.id.add_creditHours_editText);
        add_grade_radioGroup = view.findViewById(R.id.add_grade_radioGroup);
        add_submit_button = view.findViewById(R.id.add_submit_button);
        add_cancel_button = view.findViewById(R.id.add_cancel_button);

        getActivity().setTitle(getResources().getString(R.string.add_course));

        // On add_submit_button press
        add_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "add_submit_button pressed");
                if (!add_courseNumber_editText.getText().toString().isEmpty() &&
                        !add_courseName_editText.getText().toString().isEmpty() &&
                        !add_creditHours_editText.getText().toString().isEmpty()) {
                    addCourse();
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerView, GradesFragment.newInstance(mListener.getCourseList()))
                            .commit();
                }
            }
        });

        add_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "add_cancel_button pressed");
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, GradesFragment.newInstance(mListener.getCourseList()))
                        .commit();
            }
        });

        return view;
    }

    void addCourse() {
        Log.d(TAG, "addCourse running");
        if (add_grade_radioGroup.getCheckedRadioButtonId() == R.id.add_gradeA_radio) {
            Course course = new Course(
                    add_courseName_editText.getText().toString(),
                    add_courseNumber_editText.getText().toString(),
                    Integer.parseInt(add_creditHours_editText.getText().toString()),
                    4.0F
            );
            mListener.addCourse(course);
            Log.d(TAG, "addCourse - course: " + course.toString());
        } else if (add_grade_radioGroup.getCheckedRadioButtonId() == R.id.add_gradeB_radio) {
            Course course = new Course(
                    add_courseName_editText.getText().toString(),
                    add_courseNumber_editText.getText().toString(),
                    Integer.parseInt(add_creditHours_editText.getText().toString()),
                    3.0F
            );
            mListener.addCourse(course);
            Log.d(TAG, "addCourse - course: " + course.toString());
        } else if (add_grade_radioGroup.getCheckedRadioButtonId() == R.id.add_gradeC_radio) {
            Course course = new Course(
                    add_courseName_editText.getText().toString(),
                    add_courseNumber_editText.getText().toString(),
                    Integer.parseInt(add_creditHours_editText.getText().toString()),
                    2.0F
            );
            mListener.addCourse(course);
            Log.d(TAG, "addCourse - course: " + course.toString());
        } else if (add_grade_radioGroup.getCheckedRadioButtonId() == R.id.add_gradeD_radio) {
            Course course = new Course(
                    add_courseName_editText.getText().toString(),
                    add_courseNumber_editText.getText().toString(),
                    Integer.parseInt(add_creditHours_editText.getText().toString()),
                    1.0F
            );
            mListener.addCourse(course);
            Log.d(TAG, "addCourse - course: " + course.toString());
        } else if (add_grade_radioGroup.getCheckedRadioButtonId() == R.id.add_gradeF_radio) {
            Course course = new Course(
                    add_courseName_editText.getText().toString(),
                    add_courseNumber_editText.getText().toString(),
                    Integer.parseInt(add_creditHours_editText.getText().toString()),
                    0.0F
            );
            mListener.addCourse(course);
            Log.d(TAG, "addCourse - course: " + course.toString());
        } else {
            Log.e(TAG, "addCourse - Grade error");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddFragment.IListener) {
            mListener = (AddFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    AddFragment.IListener mListener;
    public interface IListener {
        void addCourse(Course course);
        ArrayList<Course> getCourseList();
    }
}