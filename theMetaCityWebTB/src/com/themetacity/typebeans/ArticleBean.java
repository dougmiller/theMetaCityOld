package com.themetacity.typebeans;

import java.util.Date;
import java.util.LinkedList;

/**
 * This is the bean that represents the an article.
 */
public class ArticleBean {

    private String title;                // The title of the article
    private String articleText;          // The actual meat of the article
    private String author;               // Who wrote the article
    private String email;                // Contacting the author
    private String dateTime;             // The dateTime/time the artcle was pubished
    private LinkedList<TagBean> tags;    // A list of the tags that this article was published under

    // Default constructor
    public ArticleBean() {
        title = "At the edge of the universe...";
        articleText = "...no articleText is good articleText.";
        author = "Flux Cap";
        email = "flux@theMetaCity.com";
        dateTime = new Date().toString();
        tags = new LinkedList<TagBean>();
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
        this.dateTime = dateTime.toString();
    }

    public LinkedList<TagBean> getTags() {
        return tags;
    }

    public void setTags(LinkedList<TagBean> tags) {
        this.tags = tags;
    }
}