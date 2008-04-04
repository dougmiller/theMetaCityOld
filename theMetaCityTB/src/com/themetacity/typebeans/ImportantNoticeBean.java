package com.themetacity.typebeans;

import java.util.Date;

/**
 * This is the class that represents important messages.
 * They have a date range that they should be shown and
 * a message and an author.
 *
 */
public class ImportantNoticeBean {
    // Class variables
    private String author;      // As the pseudonym not username
    private String message;
    private Date dateTo;
    private Date dateFrom;

    // Default constructor
    public ImportantNoticeBean() {
        author = "Flux Cap";
        message = "I am feeling attracted to you but repeled when a look a different way";
    }

    // Getters/setters
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }
}
