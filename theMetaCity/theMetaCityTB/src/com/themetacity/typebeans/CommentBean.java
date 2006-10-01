package com.themetacity.typebeans;

import java.util.Date;

/**
 * This is the bean that represents a readers comment
 */
public class CommentBean {

    String name;
    String contact;
    Date date;
    String comment;

    public CommentBean() {
        name = "Farraday";
        contact = "far@ra.day";
        date = new Date(0);
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
