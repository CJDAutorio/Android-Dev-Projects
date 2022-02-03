package edu.uncc.itcs4180.homework02;


import androidx.annotation.NonNull;

import java.io.Serializable;

public class Task implements Serializable, Comparable<Task> {
    private String name;
    private int month;
    private int day;
    private int year;
    private int priority;

    public Task(String name, int month, int day, int year, int priority) {
        this.name = name;
        this.month = month;
        this.day = day;
        this.year = year;
        this.priority = priority;
    }

    public Task() {
        this.name = "";
        this.month = 0;
        this.day = 0;
        this.year = 0;
        this.priority = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String dateToString() {
        return this.month + "/" + this.day + "/" + this.year;
    }

    public String priToString() {
        switch (this.priority) {
            case 0:
                return "Low";
            case 1:
                return "Medium";
            case 2:
                return "High";
            default:
                return "Error calculating priority!";
        }
    }

    @Override
    public int compareTo(Task task) {
        if (this.year == task.getYear()) {
            if (this.month == task.getMonth()) {
                if (this.day == task.getDay()) {
                    return this.priority - task.getPriority();
                } else {
                    return this.day - task.getDay();
                }
            } else {
                return this.month - task.getMonth();
            }
        } else {
            return this.year - task.getYear();
        }
    }
}
