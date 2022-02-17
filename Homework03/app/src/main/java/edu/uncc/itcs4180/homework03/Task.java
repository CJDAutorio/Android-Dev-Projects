package edu.uncc.itcs4180.homework03;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Task implements Serializable, Comparable<Task> {
    private String name;
    private String date;
    private String priority;
    private int day;
    private int month;
    private int year;

    public Task() {
    }

    public Task(String name, int day, int month, int year, int priorityInt) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        setDate(day, month, year);
        setPriority(priorityInt);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(int day, int month, int year) {
        date = day + "/" + month + "/" + year;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(int priorityInt) {
        switch (priorityInt) {
            case 0:
                this.priority = "High";
                break;
            case 1:
                this.priority = "Medium";
                break;
            case 2:
                this.priority = "Low";
                break;
            default:
                this.priority = "ERROR SETTING PRIORITY!";
                break;
        }
    }

    @Override
    public int compareTo(Task task) {
        if (this.year == task.getYear()) {
           if (this.month == task.getMonth()) {
               return this.day - task.getDay();
           } else {
               return this.month - task.getMonth();
           }
        } else {
            return this.year - task.getYear();
        }
    }
}
