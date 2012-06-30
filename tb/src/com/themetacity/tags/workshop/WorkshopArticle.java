package com.themetacity.tags.workshop;

import org.apache.log4j.Logger;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.themetacity.typebeans.WorkshopBean;
import com.themetacity.typebeans.TagBean;

/**
 * The custom tag used for generating the workshop article for each entry in the Workshop
 */
public class WorkshopArticle extends SimpleTagSupport {

    private static final Logger logger = Logger.getLogger(WorkshopArticle.class);

    private WorkshopBean workshop = new WorkshopBean();

    // Start processing
    public void doTag() {
        // * Initialise the context here as will not be valid in the container previously. *
        // The writer gives access to the page context so its possible to write output
        JspWriter out = getJspContext().getOut();
        try {
            out.println("    <div class=\"workshopentry\">");
            out.println("        <div class=\"left\">");
            out.println("            <h3><a href=\"/workshop/" + workshop.getId() + "\">" + workshop.getTitle() + "</a></h3>");
            out.println("            <h5>Started: " + workshop.getDateTime() + "</h5>");

             SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");

            if (workshop.getDateTime().compareTo(workshop.getTimestamp()) < 0 ) {
                out.println("            <h5>Updated: " + formatter.format(workshop.getTimestamp()) + "</h5>");
            }

            if (workshop.getTags().size() > 0) {
                out.println("            <ul>");
                for (TagBean tag : workshop.getTags()) {
                    out.println("                <li>" + tag.getTag() + "</li>");
                }
                out.println("            </ul>");
            } else {
                out.println("            <p>No tags</p>");
            }
            
            out.println("        </div>");
            out.println("        <div class=\"right\">");
            out.println("            " + workshop.getContent());
            out.println("        </div>");
            out.println("    </div>");

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