package com.themetacity.typebeans;

import java.sql.Time;
import java.util.Date;

public class NewsArticleBean implements java.io.Serializable {

    private String pictureURL;
    private String title;
    private String news;
    private String author;
    private String email;
    private Date date;
    private Time time;

    public NewsArticleBean() {  // Use the contructor to initialise the variables to a default value.
        pictureURL = "default.png";
        title = "At the edge of the universe...";
        news = "No news is good news.";
        author = "Flux";
        email = "flux@theMetaCity.com";
        date = new Date(0);
        time = new Time(0);
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