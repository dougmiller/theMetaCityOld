package com.themetacity.typebeans;

/**
 * This is the bean that repesents an authors profile
 */
public class ProfileBean {

    private String pseudonym;
    private String email;
    private String picURL;
    private String about;
    private String[] tags;

    // Default Constructor
    public ProfileBean() {
        pseudonym = "Flux Cap";
        email = "flux@themetacity.com";
        picURL = "flux";
        about = "Flux keeps the world working. Things would be different without him. " +
                "Anyone fancy a ride in his Delorian?";
        tags = new String[] {"Time Travel","Science", "Cars"};
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
