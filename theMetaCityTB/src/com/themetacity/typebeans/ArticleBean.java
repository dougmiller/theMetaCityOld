package com.themetacity.typebeans;

import java.util.Date;
import java.util.LinkedList;

/**
 * This is the bean that represents the an article.
 */
public class ArticleBean {

    private String articleID;            // The id of the article
    private String title;                // The title of the article
    private String articleText;          // The actual meat of the article
    private String author;               // Who wrote the article
    private String email;                // Contacting the author
    private String dateTime;             // The dateTime/time the artcle was pubished
    private LinkedList<TagBean> tags;    // A list of the tags that this article was published under

    /**
     * @param inputString is the title from the parameters.
     * @return a string that has the "-" removed and ready for searching.
     */
    public String extractTitle(String inputString) {
        try {
            return inputString.replace("-", " ");
        } catch (NullPointerException nullPoint) {
            return null;
        }
    }

    /**
     * Build a hypehn delimeted string
     *
     * @param toBuild String to build into the delimeed title
     * @return a String with the spaced replaced by hyphens
     */
    public String buildTitle(String toBuild) {
        return toBuild.replace(" ", "-");
    }

    public String getArticleID() {
        return articleID;
    }

    public void setArticleID(String articleID) {
        this.articleID = articleID;
    }                                                   

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
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
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm z");
        this.dateTime = formatter.format(dateTime);
    }

    public LinkedList<TagBean> getTags() {
        return tags;
    }

    public void setTags(LinkedList<TagBean> tags) {
        this.tags = tags;
    }
}