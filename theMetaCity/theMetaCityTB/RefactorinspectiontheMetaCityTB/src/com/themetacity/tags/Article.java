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
    private ArticleBean newsArticle = new ArticleBean();public Article() {
    }

    // Start processing
    public void doTag() {
        // * Initialise the context here as will not be valid in the container previously. *
        // The writer gives access to the page context so its possible to write output
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();

        try {
            out.println("<div class=\"searchnews\">");
            out.print("<img src=\"images\\"
                    //+ newsArticle.getPictureURL()
                    + " class=\"newsimage\" alt=\"News Avatar\" />");
            out.print("<span class=\"newstitle\">" + newsArticle.getTitle() + "</span><br />" +
                    "<span class=\"newsauthor\"><a href=\"mailto:" + newsArticle.getEmail() + "/>"
                    + newsArticle.getAuthor() + "</a></span><br />"
                    + newsArticle.getNews()
                    + "<hr />"
                    + "<span class=\"font10\">date: " + newsArticle.getDate().toString()
                    + " time: " + newsArticle.getTime().toString() + "</span>"
                    + "</div> <br />");
        } catch (IOException IOEx) {
            System.out.print("There was an Error");
            System.out.print(IOEx);
        }
    }

    // Free the Article used
    public void release() {
        newsArticle = null;
    }
}
