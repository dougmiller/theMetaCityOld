package com.themetacity.typebeans;

import java.sql.Time;
import java.util.Date;

/**
 * This is the bean that represents the an article.
 */
public class ArticleBean {

    private String title;       // The title of the article
    private String news;        // The actual meat of the article
    private String author;      // Who wrote the article
    private String email;       // Contacting the author
    private Date date;          // The date the artcle was pubished
    private Time time;          // The time the article was published

    // Default constructor
    public ArticleBean() {
        title = "At the edge of the universe...";
        news = "...no news is good news.";
        author = "Flux Cap";
        email = "flux@theMetaCity.com";
        date = new Date(0);
        time = new Time(0);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}