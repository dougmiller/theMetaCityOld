package com.themetacity.tags;

import com.themetacity.typebeans.ProfileBean;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * User: doug
 * com.themetacity.tags
 */

public class Profile extends TagSupport {

    ProfileBean profileBean = new ProfileBean();

    /* The writer gives access to the page context so its possible to write output */
    JspWriter out = pageContext.getOut();

    public int doStartTag() {
        profileBean.getUsername();
        profileBean.getEmail();
        profileBean.getPicURL();
        profileBean.getAbout();

        return SKIP_BODY;
    }

    public void release() {
        profileBean = null;
    }
}
