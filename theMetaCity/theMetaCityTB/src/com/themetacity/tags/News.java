package com.themetacity.tags;
/**
 * @version 1.0
 * @Author: Douglas Miller
 */

import com.themetacity.typebeans.NewsArticleBean;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * This is the custom tag that formats a NewsArticleBean into readable format. It is called in JSP pages.
 */
public class News extends TagSupport {

    // Variables
    private NewsArticleBean newsArticle = new NewsArticleBean();

    // The writer gives access to the page context so its possible to write output
    JspWriter out = pageContext.getOut();

    // Start processing
    public void doTag() {
        try {
            out.println("<div class=\"searchnews\">");
            out.print("<img src=\"images\\"
                    + newsArticle.getPictureURL()
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

    public void setNewsArticle(NewsArticleBean newsArticle) {
        this.newsArticle = newsArticle;
    }

    public NewsArticleBean getNewsArticle() {
        return newsArticle;
    }
}
