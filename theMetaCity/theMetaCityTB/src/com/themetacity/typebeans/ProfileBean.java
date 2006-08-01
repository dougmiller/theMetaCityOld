package com.themetacity.typebeans;

/**
 * User: doug
 * com.themetacity.typebeans
 */
public class ProfileBean {

    private String username;
    private String email;
    private String picURL;
    private String about;

    // Default Constructor
    public ProfileBean() {
        username = "Flux Cap";
        email = "flux@themetacity.com";
        picURL = "flux";
        about = "Flux keeps the world working. Things would be different without him." +
                "Anyone fancy a ride in his Delorian?";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
