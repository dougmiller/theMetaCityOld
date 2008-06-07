package com.themetacity.typebeans;

import java.util.Date;

/**
 * This is the class that represents important messages.
 * They have a date range that they should be shown and
 * a message and an author.
 *
 */
public class ImportantNoticeBean {
    private String messageID;
    private String username;
    private String author;      // As the pseudonym not username
    private String message;
    private Date dateTo;
    private Date dateFrom;

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
