package com.themetacity.admintags;

import com.themetacity.typebeans.ProfileBean;
import com.themetacity.typebeans.TagBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.log4j.Logger;

public class Profile extends SimpleTagSupport {

    private ProfileBean userProfile = new ProfileBean();

    static Logger logger = Logger.getLogger(Profile.class);

    /**
     * Formats the result of a profile bean into output for the page.
     */
    public void doTag() throws JspException, IOException {
        // Must initialse this here as the context will not be available before this time.
        JspWriter out = getJspContext().getOut();
        try {
            out.println("<div class=\"profile\">");
            out.println("    Pseudonym: <input type=\"text\" name=\"pseudonym\" value=\"" + userProfile.getPseudonym() + "\" size=\"15\" />");
            out.println("    Contact: <input type=\"text\" name=\"contact\" value=\"" + userProfile.getContact() + "\" size=\"30\" />");
            out.println("    <textarea name=\"about\" rows=\"10\" cols=\"65\">" + userProfile.getAbout() + "</textarea>");
            out.println("    <input type=\"submit\" name=\"submit\" value=\"update\" />");
            out.println("</div>");
        } catch (IOException IOEx) {
            logger.warn("There was an error.");
            logger.warn(IOEx);
        }
    }

    /**
     * Releases the used variables.
     */
    public void release() {
        userProfile = null;
    }

    public ProfileBean getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(ProfileBean userProfile) {
        this.userProfile = userProfile;
    }
}