package com.themetacity.typebeans;

import java.util.Date;
import java.util.LinkedList;

/**
 * This is the bean that represents the an article.
 */
public class ArticleBean {

    private String articleID;            // The id of the article
    private String title;                // The title of the article
    private String URL;                  // The URL for this article
    private String articleText;          // The actual meat of the article
    private String email;                // Contacting the author
    private Date createdDate;               // The date the article was published
    private Date modifiedDate;              // The date the article was modified
    private LinkedList<TagBean> tags;    // A list of the tags that this article was published under

    /**
     * Build a URL friendly string from the given title
     * A hyphen delimited string, stripped of non url friendly characters and potential punctuation errors
     *
     * @param toBuild String to build into the delimited url
     * @return a String with the space replaced by hyphens
     */
    public String buildURL(String toBuild) {
        toBuild = toBuild.trim();                     // Remove inappropriate whitespace
        toBuild = toBuild.replaceAll("[^\\w ]", "");  // Remove non URL friendly characters (inc multiple spaces)
        toBuild = toBuild.replaceAll(" +", "-");      // Change the spaces to hyphens

        return toBuild;
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
        this.title = title.trim();
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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