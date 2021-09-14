package com.enestigli.instaclonejava.model;

public class Post {
    String email ;
    String comment ;
    String downloadURL;

    public Post(String email, String comment, String downloadURL) {
        this.email = email;
        this.comment = comment;
        this.downloadURL = downloadURL;
    }
}
