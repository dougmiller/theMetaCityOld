package com.themetacityweb.typebeans;

import java.util.LinkedList;
import java.util.Date;

public class ArchiveEntryBean {

    private String title;                // The title of the article
    private String author;               // Who wrote the article
    private String dateTime;             // The dateTime/time the artcle was pubished

    // Default constructor
    public ArchiveEntryBean() {
        title = "At the edge of the universe...";
        author = "Flux Cap";
        dateTime = new Date().toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime.toString();
    }
}
