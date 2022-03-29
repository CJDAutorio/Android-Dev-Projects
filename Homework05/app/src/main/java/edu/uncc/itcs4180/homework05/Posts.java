package edu.uncc.itcs4180.homework05;

import androidx.annotation.Nullable;

import java.util.Arrays;

/**
 * Homework 05
 * Posts.java
 * CJ D'Autorio
 */
public class Posts {
    public String status;
    public Post[] posts;

    public Posts() {
    }

    public Posts(String status, Post[] posts) {
        this.status = status;
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "status='" + status + '\'' +
                ", posts=" + Arrays.toString(posts) +
                '}';
    }

    public class Post {
        public String post_id;
        public String post_text;
        public String created_at;
        public String created_by_uid;
        public String created_by_name;

        public Post() {
        }

        @Override
        public String toString() {
            return "Post{" +
                    "post_id='" + post_id + '\'' +
                    ", post_text='" + post_text + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", created_by_uid='" + created_by_uid + '\'' +
                    ", created_by_name='" + created_by_name + '\'' +
                    '}';
        }

        public Post(String post_id, String post_text, String created_at, String created_by_uid, String created_by_name) {
            this.post_id = post_id;
            this.post_text = post_text;
            this.created_at = created_at;
            this.created_by_uid = created_by_uid;
            this.created_by_name = created_by_name;
        }

        public String getDate() {
            String[] splitString = created_at.split(" ");
            return splitString[0];
        }

        public String getTime() {
            String[] splitString = created_at.split(" ");
            return splitString[1];
        }
    }
}
