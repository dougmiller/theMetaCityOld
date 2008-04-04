package com.themetacity.admintags;

import com.themetacity.typebeans.LinkBean;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 */
public class Links extends SimpleTagSupport {

    private LinkBean linkBean = new LinkBean();

    static Logger logger = Logger.getLogger(Links.class);

    public void doTag() {
        // * Initialise the context here as will not be valid in the container previously. *
        // The writer gives access to the page context so its possible to write output
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();

        try {
            out.println("<div>" +
                    "<input type=\"text\" name=\"descText\" value=\"" + linkBean.getDescText() + "\"/>" +
                    "<input type=\"text\" name=\"linkURL\" value=\"" + linkBean.getLinkURL() + "\"/>" +
                    "<input type=\"hidden\" name=\"linkID\" value=\"" + linkBean.getLinkID() + "\"/>" +
                    "<button type=\"submit\" class=\"reloadbutton\" name=\"submit\" value=\"update\"><img src=\"siteimages/tick.png\" alt=\"Update\"/>Update</button>" +
                    "<button type=\"submit\" class=\"reloadbutton\" name=\"submit\" value=\"delete\"><img src=\"siteimages/tick.png\" alt=\"Delete\"/>Delete</button>" +
                    "</div>");
        } catch (IOException IOEx) {
            logger.warn("There was an error with the article rendering");
            logger.warn(IOEx);
        }
    }

    // Free the Article used
    public void release() {
        linkBean = null;
    }

    public LinkBean getLinkBean() {
        return linkBean;
    }

    public void setLinkBean(LinkBean linkBean) {
        this.linkBean = linkBean;
    }
}
