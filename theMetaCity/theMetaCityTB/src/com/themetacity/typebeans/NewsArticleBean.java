package com.themetacity.typebeans;

import java.sql.Time;
import java.util.Date;

/**
 * This is the bean that represents the news posts.
 */
public class NewsArticleBean implements java.io.Serializable {

    private String pictureURL;
    private String title;
    private String news;
    private String author;
    private String email;
    private Date date;
    private Time time;

    /**
     * Use this contructor to initialise the variables to a default value.
     */
    public NewsArticleBean() {
        this.pictureURL = "flux.png";
        this.title = "At the edge of the universe...";
        this.news = "...no news is good news.";
        this.author = "Flux";
        this.email = "flux@theMetaCity.com";
        this.date = new Date(0);
        this.time = new Time(0);
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
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