package com.themetacity.typebeans;

import java.util.Date;

/**
 * This is the bean that represents a readers comment
 */
public class CommentBean {

    private String name;            // The name of the author
    private String contact;         // How to contact the author, either email or webpage
    private Date date;              // The date the comment was posted
    private String comment;         // The actual comment

    // Default constructor
    public CommentBean() {
        name = "Michael Faraday";
        contact = "m.fa@ra.day";
        date = new Date();
        comment = "I luv Flux!";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
