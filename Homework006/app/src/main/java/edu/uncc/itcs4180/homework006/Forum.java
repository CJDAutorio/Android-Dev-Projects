package edu.uncc.itcs4180.homework006;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
 * Homework 06
 * Forum.java
 * CJ D'Autorio
 */
public class Forum {
    private String postId;
    private String title;
    private String body;
    private Calendar date = Calendar.getInstance();
    private ArrayList<String> likes;
    private String userId;
    private ArrayList<Map<String, Object>> comments;
    private String name;

    public Forum() {
    }

    public Forum(String postId, String title, String body, Timestamp date, ArrayList<String> likes, String userId, ArrayList<Map<String, Object>> comments, String name) {
        this.postId = postId;
        this.title = title;
        this.body = body;
        this.date.setTime(date.toDate());
        this.likes = likes;
        this.userId = userId;
        this.comments = comments;
        this.name = name;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date.setTime(date.toDate());
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<Map<String, Object>> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Map<String, Object>> comments) {
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Forum{" +
                "postId='" + postId + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", date=" + date +
                ", likes=" + likes +
                ", userId='" + userId + '\'' +
                ", comments=" + comments +
                ", name='" + name + '\'' +
                '}';
    }
}
