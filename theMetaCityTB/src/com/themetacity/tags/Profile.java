package com.themetacity.tags;

import com.themetacity.typebeans.ProfileBean;
import com.themetacity.typebeans.TagBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * This is the custom tag that formats a ProfileBean into readable format. It is called in a JSP page.
 */
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
            out.println("    <h2>" + userProfile.getPseudonym() + "</h2>");
            out.println("    <h2>" + userProfile.getContact() + "</h2>");
            out.println("    " + userProfile.getAbout());
            out.println("        <p class=\"tagsspan\">" + formatTags(userProfile.getTags()) + "</p>");
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

    private String formatTags(LinkedList<TagBean> tagList) {
        StringBuilder outputString = new StringBuilder();

        for (TagBean tag : tagList) {
            outputString.append("<a href=\"/tags/").append(tag.getTag()).append("\">").append(tag.getTag()).append("(").append(tag.getNumTimesTagUsed()).append(")").append("</a> ");
        }
        // There are not tagss for this user
        if (outputString.length() == 0) {
            outputString.append("This user has not posted any tags.");
        }

        return outputString.toString().trim();
    }

    public ProfileBean getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(ProfileBean userProfile) {
        this.userProfile = userProfile;
    }
}