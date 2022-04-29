package edu.uncc.itcs4180.inclass10;

import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CoursesRecyclerViewAdapter extends RecyclerView.Adapter<CoursesRecyclerViewAdapter.CoursesViewHolder> {
    ArrayList<Course> courseArrayList;
    ICoursesRecycler mListener;

    public CoursesRecyclerViewAdapter(ArrayList<Course> courseArrayList, ICoursesRecycler mListener) {
        this.courseArrayList = courseArrayList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_row_item,
                parent, false);

        CoursesViewHolder coursesViewHolder = new CoursesViewHolder(view, mListener);

        return coursesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesViewHolder holder, int position) {
        Resources resources = holder.itemView.getContext().getResources();
        Course course = courseArrayList.get(position);
        holder.course = course;
        holder.courseItem_grade_textView.setText(setGrade(course));
        holder.courseItem_name_textView.setText(course.name);
        holder.courseItem_number_textView.setText(course.number);
        holder.courseItem_creditHours_textView.setText(resources.getString(R.string.num_credit_hours, course.creditHours));
    }

    @Override
    public int getItemCount() {
        return courseArrayList.size();
    }

    private String setGrade(Course course) {
        if (course.grade == 4.0F) {
            return "A";
        } else if (course.grade == 3.0F) {
            return "B";
        } else if (course.grade == 2.0F) {
            return "C";
        } else if (course.grade == 1.0F) {
            return "D";
        } else {
            return "F";
        }
    }


    public static class CoursesViewHolder extends RecyclerView.ViewHolder {
        ICoursesRecycler mListener;
        Course course;
        TextView courseItem_grade_textView;
        TextView courseItem_number_textView;
        TextView courseItem_name_textView;
        TextView courseItem_creditHours_textView;
        ImageButton courseItem_trash_button;

        public CoursesViewHolder(@NonNull View itemView, ICoursesRecycler mListener) {
            super(itemView);
            this.mListener = mListener;
            courseItem_grade_textView = itemView.findViewById(R.id.courseItem_grade_textView);
            courseItem_number_textView = itemView.findViewById(R.id.courseItem_number_textView);
            courseItem_name_textView = itemView.findViewById(R.id.courseItem_name_textView);
            courseItem_creditHours_textView = itemView.findViewById(R.id.courseItem_creditHours_textView);
            courseItem_trash_button = itemView.findViewById(R.id.courseItem_trash_button);

            // On courseItem_trash_button press
            courseItem_trash_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.deleteCourse(course);
                }
            });
        }
    }

    interface  ICoursesRecycler {
        void deleteCourse(Course course);
    }
}
