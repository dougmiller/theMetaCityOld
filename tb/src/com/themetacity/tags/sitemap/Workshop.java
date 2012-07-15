package com.themetacity.tags.sitemap;

import com.themetacity.typebeans.WorkshopBean;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * This is the custom tag that formats a ArticleBean into sitemap entries.
 */
public class Workshop extends SimpleTagSupport {

    private WorkshopBean workshop = new WorkshopBean();

    private static final Logger logger = Logger.getLogger(Workshop.class);

    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");

    public void doTag() {
        JspWriter out = getJspContext().getOut();
        try {
            out.println("    <url>");
            out.println("        <loc>http://www.themetacity.com/workshop/" + workshop.getId() + "</loc>");
            out.println("        <lastmod>" + formatter.format(workshop.getModifiedDate()) + "</lastmod>");
            out.println("        <changefreq>monthly</changefreq>");
            out.println("        <priority>0.8</priority>");
            out.println("    </url>");

        } catch (IOException IOEx) {
            logger.warn("There was an error with the article rendering");
            logger.warn(IOEx);
        }
    }

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
