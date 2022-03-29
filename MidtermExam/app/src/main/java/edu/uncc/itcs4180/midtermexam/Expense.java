package edu.uncc.itcs4180.midtermexam;

public class Expense implements Comparable<Expense> {
    private String name;
    private double amount;
    private int day;
    private int month;
    private int year;
    private String category;

    public Expense(String name, float amount, int day, int month, int year, String category) {
        this.name = name;
        this.amount = amount;
        this.day = day;
        this.month = month;
        this.year = year;
        this.category = category;
    }

    public Expense() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDateToString() {
        return String.format("%02d", this.month) + "/" + String.format("%02d", this.day) + "/" + this.year;
    }

    @Override
    public int compareTo(Expense expense) {
        if (this.year == expense.getYear()) {
            if (this.month == expense.getMonth()) {
                return this.day - expense.getDay();
            } else {
                return this.month - expense.getMonth();
            }
        } else {
            return this.year - expense.getYear();
        }
    }
}
