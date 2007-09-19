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

    public Profile() {
    }

    /**
     * Formats the result of a profile bean into output for the page.
     *
     * @return SKIP_BODY
     */
    public int doStartTag() {
        // Must initialse this here as the context will not be available before this time.
        JspWriter out=pageContext.getOut();
        try {
            out.println("<div class=\"profile\">");
            //out.println("<img src=\"" + userProfile.getPicURL() + "\" alt=\"User profile picture\"");
            out.println("<span class=\"pseudonym\">" + userProfile.getPseudonym() + "</span><br />");
            out.println("blah" + userProfile.getEmail() + "sometttting<br />");
            out.println(userProfile.getAbout() + "<br />");
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
}