package com.themetacity.tags.workshop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.themetacity.typebeans.WorkshopBean;
import com.themetacity.typebeans.TagBean;

/**
 * The custom tag used for generating the workshop blurb entries on the Workshop index page
 */
public class WorkshopBlurb extends SimpleTagSupport {

    private static final Logger logger = LogManager.getLogger(WorkshopBlurb.class);

    private WorkshopBean workshop = new WorkshopBean();

    // Start processing
    public void doTag() {
        // * Initialise the context here as will not be valid in the container previously. *
        // The writer gives access to the page context so its possible to write output
        JspWriter out = getJspContext().getOut();
        try {
            out.println("    <article class=\"workshopentry\">");
            out.println("        <header>");
            out.println("            <h1><a href=\"/workshop/" + workshop.getId() + "\">" + workshop.getTitle() + "</a></h1>");
            out.println("            <h5>Started: " + workshop.getCreatedDate() + "</h5>");

            SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
            if (workshop.getComplete()) {  // Project has been completed
                out.println("            <h5>Completed: " + formatter.format(workshop.getModifiedDate()) + "</h5>");
            } else if (!workshop.getCreatedDate().equals(workshop.getModifiedDate())) {  // Not completed but has been updated
                out.println("            <h5>Updated: " + formatter.format(workshop.getModifiedDate()) + "</h5>");
            }

            if (workshop.getTags().size() > 0) {
                out.println("            <ul>");
                for (TagBean tag : workshop.getTags()) {
                    out.println("               <li><a href=\"/workshop/tag/" + tag.getTag() + "\">" + tag.getTag() + "</a></li>");        // need an actual link that actually goes somewhere
                }
                out.println("            </ul>");
            } else {
                out.println("            <p>No tags</p>");
            }
            out.println("        </header>");
            out.println("        <div class=\"right\">");
            out.println("            " + workshop.getBlurb());
            out.println("        </div>");
            out.println("    </article>");

        } catch (IOException IOEx) {
            logger.warn("There was an error with the workshop rendering tag.");
            logger.warn(IOEx);
        }
    }

    // Free the Article used
    public void release() {
        workshop = null;
    }

    public WorkshopBean getWorkshop() {
        return workshop;
    }

    public void setWorkshop(WorkshopBean workshop) {
        this.workshop = workshop;
    }
}
