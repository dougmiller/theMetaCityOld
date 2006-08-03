package com.themetacity.tags;

import com.themetacity.typebeans.ProfileBean;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * This is the custom tag that formats a ProfileBean into readable format. It is called in a JSP page.
 */
public class Profile extends TagSupport {

    ProfileBean profileBean = new ProfileBean();

    /* The writer gives access to the page context so its possible to write output */
    JspWriter out = pageContext.getOut();

    //todo make better docs for this
    /**
     * Process the work.
     * @return SKIP_BODY
     */
    public int doStartTag() {
        profileBean.getUsername();
        profileBean.getEmail();
        profileBean.getPicURL();
        profileBean.getAbout();

        return SKIP_BODY;
    }

    /**
     * Releases the used variables.
     */
    public void release() {
        profileBean = null;
    }
}