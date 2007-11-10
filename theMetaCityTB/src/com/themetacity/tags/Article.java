package com.themetacity.tags;
/**
 * @version 1.0
 * @Author: Douglas Miller
 */

import com.themetacity.typebeans.ArticleBean;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * This is the custom tag that formats a ArticleBean into readable format. It is called in JSP pages.
 */
public class Article extends SimpleTagSupport {

    // Variables
    private ArticleBean newsArticle = new ArticleBean();

    // Start processing
    public void doTag() {
        // * Initialise the context here as will not be valid in the container previously. *
        // The writer gives access to the page context so its possible to write output
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();

        try {
            out.println("    <div class=\"newsarticle\">");
            out.println("        <h2><a href=\"\">" + newsArticle.getTitle() + "</a></h2>");
            out.println("        " + newsArticle.getNews());
            out.println("        <span>" + newsArticle.getDate() + "</span>");
            out.println("    </div>");


        } catch (IOException IOEx) {
            System.out.print("There was an error with the article rendering");
            System.out.print(IOEx);
        }
    }

    // Free the Article used
    public void release() {
        newsArticle = null;
    }

    public ArticleBean getNewsArticle() {
        return newsArticle;
    }

    public void setNewsArticle(ArticleBean newsArticle) {
        this.newsArticle = newsArticle;
    }


}
