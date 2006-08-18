package com.themetacity.tags;

import com.themetacity.typebeans.ProfileBean;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * This is the custom tag that formats a ProfileBean into readable format. It is called in a JSP page.
 */
public class Profile extends TagSupport {

    ProfileBean profileBean = new ProfileBean();

    /* The writer gives access to the page context so its possible to write output */
    JspWriter out = pageContext.getOut();

    /**
     * Process the work.
     *
     * @return SKIP_BODY
     */
    public int doStartTag() {

        try {
            out.println("<div>");
            out.println(profileBean.getUsername());
            out.println(profileBean.getEmail());
            out.println(profileBean.getPicURL());
            out.println(profileBean.getAbout());
            out.println("</div>");

        }
        catch (IOException IOex) {
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