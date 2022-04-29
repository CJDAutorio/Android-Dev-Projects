package edu.uncc.itcs4180.inclass10;

import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "courses")
public class Course implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String number;

    @ColumnInfo
    public int creditHours;

    @ColumnInfo
    public float grade;

    public Course(long id, String name, String number, int creditHours, float grade) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.creditHours = creditHours;
        this.grade = grade;
    }

    public Course(String name, String number, int creditHours, float grade) {
        this.name = name;
        this.number = number;
        this.creditHours = creditHours;
        this.grade = grade;
    }

    public Course() {
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", creditHours=" + creditHours +
                ", grade='" + grade + '\'' +
                '}';
    }
}
