package com.themetacity.typebeans;

import java.util.Date;

/**
 * This is the bean that represents a readers comment
 */
public class CommentBean {

    private String name;            // The name of the author
    private String contact;         // How to contact the author, either email or webpage
    private String dateTime;        // The dateTime the comment was posted
    private String comment;         // The actual comment

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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime.toString();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
