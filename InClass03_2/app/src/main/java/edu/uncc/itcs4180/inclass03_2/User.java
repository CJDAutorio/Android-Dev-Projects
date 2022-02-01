package edu.uncc.itcs4180.inclass03_2;

import java.io.Serializable;

public class User implements Serializable {
    private String username = "";
    private String email = "";
    private int id = -1;
    private String dept = "";

    public User() {
    }

    public User(String username, String email, int id, String dept) {
        this.username = username;
        this.email = email;
        this.id = id;
        this.dept = dept;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
