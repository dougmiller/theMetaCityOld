package com.themetacity.tags;

import com.themetacity.typebeans.ArticleBean;
import com.themetacity.typebeans.TagBean;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * This is the custom tag that formats a ArticleBean into readable format. It is called in JSP pages.
 */
public class SitemapTags extends SimpleTagSupport {

    // Variables
    private TagBean tag = new TagBean();

    private static final Logger logger = Logger.getLogger(Article.class);

    // Start processing
    public void doTag() {
        // * Initialise the context here as will not be valid in the container previously. *
        // The writer gives access to the page context so its possible to write output
        JspWriter out = getJspContext().getOut();
        //logger.debug("This is a test to send over a custom socket");
        try {
            out.println("    <url>");
            out.println("        <loc>http://www.themetacity.com/tags/" + tag.getTag() + "</loc>");
            out.println("        <lastmod>" + tag.getLastUpdatedDate().toString() + "</lastmod>");
            out.println("        <changefreq>monthly</changefreq>");
            out.println("        <priority>0.4</priority>");
            out.println("    </url>");

        } catch (IOException IOEx) {
            logger.warn("There was an error with the tag rendering");
            logger.warn(IOEx);
        }
    }

    // Free the Article used
    public void release() {
        tag = null;
    }

    public TagBean getTag() {
        return tag;
    }

    public void setTag(TagBean tag) {
        this.tag = tag;
    }
}
