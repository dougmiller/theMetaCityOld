package com.themetacity.typebeans;

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
    /* NB There are date fields in the database but they do not need
       to be represented here. (Mabey...we will see) */

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
}
