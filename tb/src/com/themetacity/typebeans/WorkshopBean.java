package com.themetacity.typebeans;

import java.util.Date;
import java.util.LinkedList;

/**
 * This is the bean that represents the an article.
 */
public class WorkshopBean {

    private int id;                      // The id of the article
    private String title;                // The title of the article
    private String blurb;                // The blurb/short description for the workshop index;
    private String content;              // The actual meat of the article
    private Boolean complete;               // Whether the project is complete or not
    private Date createdDate;            // The date the article was published
    private Date modifiedDate;           // The date the article was modified
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

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public LinkedList<TagBean> getTags() {
        return tags;
    }

    public void setTags(LinkedList<TagBean> tags) {
        this.tags = tags;
    }
}