package com.themetacity.tags;

import com.themetacity.typebeans.ProfileBean;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * This is the custom tag that formats a ProfileBean into readable format. It is called in a JSP page.
 */
public class Profile extends TagSupport {

    private ProfileBean userProfile = new ProfileBean();

    /* The writer gives access to the page context so its possible to write output */
    JspWriter out = pageContext.getOut();

    /**
     * Formats the result of a profile bean into output for the page.
     *
     * @return SKIP_BODY
     */
    public int doStartTag() {

        try {
            out.println("<div>");
            out.println(userProfile.getPseudonym() + " ");
            out.println(userProfile.getEmail() + " ");
            //out.println(userProfile.getPicURL() + " ");
            out.println(userProfile.getAbout() + " ");
            out.println("</div>");

        } catch (IOException IOex) {
            System.out.println("There was an error.");
        }

        return SKIP_BODY;
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