package com.themetacity.tags;

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
            out.println("<li>" + buildLink(linkBean) +"</li>");
        } catch (IOException IOEx) {
            logger.warn("There was an error with the article rendering");
            logger.warn(IOEx);
        }
    }

    // Free the Article used
    public void release() {
        linkBean = null;
    }

    private String buildLink(LinkBean linkBean) {
        return "<a href=\"" + linkBean.getLinkURL() + "\">" + linkBean.getDescText() + "</a>";
    }

    public LinkBean getLinkBean() {
        return linkBean;
    }

    public void setLinkBean(LinkBean linkBean) {
        this.linkBean = linkBean;
    }
}
