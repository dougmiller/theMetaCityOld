package com.themetacity.tags;

import com.themetacity.typebeans.ArticleBean;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * This is the custom tag that formats a ArticleBean into readable format. It is called in JSP pages.
 */
public class SitemapArticle extends SimpleTagSupport {

    // Variables
    private ArticleBean article = new ArticleBean();

    private static final Logger logger = Logger.getLogger(Article.class);

    // Start processing
    public void doTag() {
        // * Initialise the context here as will not be valid in the container previously. *
        // The writer gives access to the page context so its possible to write output
        JspWriter out = getJspContext().getOut();
        //logger.debug("This is a test to send over a custom socket");
        try {
            out.println("    <url>");
            out.println("        <loc>http://www.themetacity.com/" + article.getURL() + "</loc>");
            out.println("        <lastmod>" + formatTimestamp(article.getTimestamp()) + "</lastmod>");
            out.println("        <changefreq>monthly</changefreq>");
            out.println("        <priority>0.8</priority>");
            out.println("    </url>");

        } catch (IOException IOEx) {
            logger.warn("There was an error with the article rendering");
            logger.warn(IOEx);
        }
    }

    // Free the Article used
    public void release() {
        article = null;
    }

    private String formatTimestamp(String timestamp) {
        return timestamp.split(" ")[0];
    }

    public ArticleBean getArticle() {
        return article;
    }

    public void setArticle(ArticleBean article) {
        this.article = article;
    }
}
