package com.themetacity.admintags;

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
            out.println("<form action=\"users.jsp\" method=\"get\">");
            out.println("<div class=\"profile\">");
            out.println("    Pseudonym: <input type=\"text\" name=\"pseudonym\" value=\"" + userProfile.getPseudonym() + "\" size=\"15\" />");
            out.println("    Contact: <input type=\"text\" name=\"contact\" value=\"" + userProfile.getContact() + "\" size=\"30\" />");
            out.println("    <textarea name=\"about\" rows=\"10\" cols=\"65\">" + userProfile.getAbout() + "</textarea>");
            out.println("    <input type=\"submit\" name=\"submit\" value=\"update\" />");
            out.println("</div>");
            out.println("</form>");


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
            outputString.append("<a href=\"/tags.jsp?tag=").append(tag.getTag()).append("\">").append(tag.getTag()).append("(").append(tag.getNumTimesTagUsed()).append(")").append("</a> ");
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