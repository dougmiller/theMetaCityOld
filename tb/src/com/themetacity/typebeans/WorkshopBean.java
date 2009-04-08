package com.themetacity.typebeans;

import java.util.Date;
import java.util.LinkedList;

/**
 * This is the bean that represents the an article.
 */
public class WorkshopBean {

    private int id;                   // The id of the article
    private String title;                // The title of the article
    private String blurb;                // The blurb/short description for the workshop index;
    private String content;              // The actual meat of the article
    private String author;               // Who wrote the article
    private String email;                // Contacting the author
    private String dateTime;             // The dateTime/time the artcle was pubished
    private String timestamp;            // The dateTime/time the artcle was pubished
    private LinkedList<TagBean> tags;    // A list of the tags that this article was published under

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.trim();
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.dateTime = formatter.format(dateTime);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.timestamp = formatter.format(timestamp);
    }

    public LinkedList<TagBean> getTags() {
        return tags;
    }

    public void setTags(LinkedList<TagBean> tags) {
        this.tags = tags;
    }
}