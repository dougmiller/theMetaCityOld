package com.themetacity.tags.workshop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

import com.themetacity.typebeans.WorkshopBean;

/**
 * The custom tag that generates links to projects.
 */
public class WorkshopLink extends SimpleTagSupport {

    private static final Logger logger = LogManager.getLogger(WorkshopLink.class);

    private WorkshopBean workshopLinkBean = new WorkshopBean();

    // Start processing
    public void doTag() {
        // * Initialise the context here as will not be valid in the container previously. *
        // The writer gives access to the page context so its possible to write output
        JspWriter out = getJspContext().getOut();
        try {
            out.println("    <li><a href=\"/workshop/" + workshopLinkBean.getId() + "\">" + workshopLinkBean.getTitle() + "</a></li>");
        } catch (IOException IOEx) {
            logger.warn("There was an error with the workshopLinkBean rendering tag.");
            logger.warn(IOEx);
        }
    }

    // Free the Article used
    public void release() {
        workshopLinkBean = null;
    }

    public WorkshopBean getWorkshopLinkBean() {
        return workshopLinkBean;
    }

    public void setWorkshopLinkBean(WorkshopBean workshopLinkBean) {
        this.workshopLinkBean = workshopLinkBean;
    }
}
