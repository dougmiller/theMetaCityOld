package com.themetacity.tags;

import com.themetacity.typebeans.ProfileBean;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.PrintStream;

/**
 * This is the custom tag that formats a ProfileBean into readable format. It is called in a JSP page.
 */
public class Profile extends TagSupport {

    ProfileBean profileBean = new ProfileBean();

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
            out.println(profileBean.getUsername() + " ");
            out.println(profileBean.getEmail() + " ");
            out.println(profileBean.getPicURL() + " ");
            out.println(profileBean.getAbout() + " ");
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
        profileBean = null;
    }
}