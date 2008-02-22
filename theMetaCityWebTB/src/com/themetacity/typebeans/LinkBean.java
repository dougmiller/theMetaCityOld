package com.themetacity.typebeans;

import java.util.Date;

/**
 */
public class LinkBean {
    private String linkURL;
    private String descText;
    private String datePosted;

    public LinkBean(){
        linkURL = "http://www.themetacity.com/";
        descText = "theMetacity.com is great!";
        datePosted = new Date().toString();
    }

    public String getLinkURL() {
        return linkURL;
    }

    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }
}
