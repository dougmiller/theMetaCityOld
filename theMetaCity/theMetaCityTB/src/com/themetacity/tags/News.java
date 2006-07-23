package com.themetacity.tags;
/**
 * @version 1.0
 * @Author: Douglas Miller
 */

import com.themetacity.beans.NewsArticleBean;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class News extends TagSupport {

    /* Variables */
    NewsArticleBean newsArticle = new NewsArticleBean();
            //getNewsArticle()
            //new NewsArticleBean()

    /* The writer gives access to the page context so its possible to write output */
    JspWriter out = pageContext.getOut();

    /* Start processing */
    public int doStartTag() {
        try {
            out.println("<div class=\"searchnews\">");

            if (newsArticle.getPictureURL() == (null)) {
                out.print("<img src=\"images\\default.png\" class=\"newsimage\" alt=\"User picture\"/>");
            } else {
                out.print("<img src=\"images\\"
                        + newsArticle.getPictureURL()
                        + " class=\"newsimage\" alt=\"News Avatar\" />");
            }

            out.print("<span class=\"newstitle\">" + newsArticle.getTitle() + "</span><br />" +
                    "<span class=\"newsauthor\"><a href=\"mailto:" + newsArticle.getEmail() + "/>" + newsArticle.getAuthor() + "</a></span><br />"
                    + newsArticle.getNews()
                    + "<hr />"
                    + "<span class=\"font10\">date: " + newsArticle.getDate().toString()
                    + " time: " + newsArticle.getTime().toString() + "</span>"
                    + "</div> <br />");
        } catch (Exception e) {
            System.out.print("There was an Error");
            System.out.print(e);
        }
        /* Nothing else needs to be done or evaluated to skip to the end of the tag */
        return SKIP_BODY;
    }

    /* Free the Article used */
    public void release() {
        newsArticle = null;
    }

    public NewsArticleBean getNewsArticle() {
        return newsArticle;
    }

    public void setNewsArticle(NewsArticleBean newsArticle) {
        this.newsArticle = newsArticle;
    }
}
