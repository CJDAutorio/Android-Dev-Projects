package edu.uncc.itcs4180.firebasetutorial;

public class User {
    String name;
    String cell;

    public User() {
    }

    public User(String name, String cell) {
        this.name = name;
        this.cell = cell;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    @Override
    public String toString() {
        return name;
    }
}
