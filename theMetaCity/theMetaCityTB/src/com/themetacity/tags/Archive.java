package com.themetacity.tags;
/**
 * @version 1.0
 * @Author: Douglas Miller
 */

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class Archive extends TagSupport {

    /* Variables */

    /* The writer gives access to the page context so its possible to write output */
    JspWriter out = pageContext.getOut();

    public int doStartTag() {  //comment
        return SKIP_BODY;
    }

    public void release() {

    }
}