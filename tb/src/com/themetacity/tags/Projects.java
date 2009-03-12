package com.themetacity.tags;

import com.themetacity.typebeans.ProjectBean;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 */
public class Projects extends SimpleTagSupport {

    private ProjectBean projectBean = new ProjectBean();

    static Logger logger = Logger.getLogger(Projects.class);

    public void doTag() {
        // * Initialise the context here as will not be valid in the container previously. *
        // The writer gives access to the page context so its possible to write output
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();

        try {
            out.println("<li>" + buildLink(projectBean) +"</li>");
        } catch (IOException IOEx) {
            logger.warn("There was an error with the article rendering");
            logger.warn(IOEx);
        }
    }

    // Free the Article used
    public void release() {
        projectBean = null;
    }

    private String buildLink(ProjectBean linkBean) {
        return "<a href=\"" + linkBean.getProjectURL() + "\">" + linkBean.getDescText() + "</a>";
    }

    public ProjectBean getProjectBean() {
        return projectBean;
    }

    public void setProjectBean(ProjectBean projectBean) {
        this.projectBean = projectBean;
    }
}
