package com.themetacityweb.typebeans;

import java.util.LinkedList;

/**
 * This is the bean that repesents an authors profile for display in the about page.
 *
 * @Author Douglas Miller
 * @Date 11 Nov 2007
 */
public class ProfileBean {

    private String pseudonym;
    private String contact;
    private String about;
    private LinkedList<TagBean> tags;

    // Default Constructor
    public ProfileBean() {
        pseudonym = "Flux Cap";
        contact = "flux@themetacity.com";
        about = "Flux keeps the world working. Things would be different without him. " +
                "Anyone fancy a ride in his Delorian?";
        tags = new LinkedList<TagBean>();
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public LinkedList<TagBean> getTags() {
        return tags;
    }

    public void setTags(LinkedList<TagBean> tags) {
        this.tags = tags;
    }
}
