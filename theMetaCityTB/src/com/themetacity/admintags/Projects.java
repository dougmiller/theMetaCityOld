package com.themetacity.admintags;

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
            out.println("<div>" +
                    "<a href=\"" + projectBean.getProjectURL() + "\"><img src=\"siteimages/link_go.png\" alt=\"Visit Link\"/></a>" +
                    "<input type=\"text\" name=\"descText\" value=\"" + projectBean.getDescText() + "\"/>" +
                    "<input type=\"text\" name=\"linkURL\" value=\"" + projectBean.getProjectURL() + "\"/>" +
                    "<input type=\"hidden\" name=\"linkID\" value=\"" + projectBean.getProjectID() + "\"/>" +
                    "<button type=\"submit\" class=\"submitbutton\" name=\"submit\" value=\"update\"><img src=\"siteimages/tick.png\" alt=\"Update\"/>Update</button>" +
                    "<button type=\"submit\" class=\"deletebutton\" name=\"submit\" value=\"delete\"><img src=\"siteimages/delete.png\" alt=\"Delete\"/>Delete</button>" +
                    "</div>");
        } catch (IOException IOEx) {
            logger.warn("There was an error with the article rendering");
            logger.warn(IOEx);
        }
    }

    // Free the Article used
    public void release() {
        projectBean = null;
    }

    public ProjectBean getProjectBean() {
        return projectBean;
    }

    public void setProjectBean(ProjectBean projectBean) {
        this.projectBean = projectBean;
    }
}
