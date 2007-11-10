package com.themetacity.tags;

import com.themetacity.typebeans.ProfileBean;
import com.themetacity.typebeans.TagBean;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * This is the custom tag that formats a ProfileBean into readable format. It is called in a JSP page.
 */
public class Profile extends TagSupport {

    private ProfileBean userProfile = new ProfileBean();

    /* The writer gives access to the page context so its possible to write output */
    JspWriter out;

    /**
     * Formats the result of a profile bean into output for the page.
     *
     * @return SKIP_BODY
     */
    public int doStartTag() {
        // Must initialse this here as the context will not be available before this time.
        out = pageContext.getOut();
        try {
            out.println("<div class=\"profile\">");
            out.println("    <div class=\"profileheader\">");
            out.println("        <div class=\"profilepiccontainer\">");
            //out.println("            <img src=\"images/profileicons/" + userProfile.g + ".png\" alt=\"User profile picture\" />");
            out.println("        </div>");
            out.println("        <div class=\"profileinfo\">");
            out.println("            <span class=\"pseudonym\">" + userProfile.getPseudonym() + "</span><br />");
            out.println("            <span class=\"contact\">" + userProfile.getContact() + "</span><br />");
            out.println("        </div>");
            out.println("    </div>");
            out.println("    <div>" + userProfile.getAbout() + "</div>");
            out.println("    <div class=\"tags\">");
            for (TagBean tag : userProfile.getTags()) {
                out.print("" + tag.getTag() + "(" + tag.getNumTimesTagUsed() + ")");
            }
            out.println("    </div>");
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