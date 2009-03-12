package com.themetacity.rsstags;

import com.themetacity.typebeans.ArticleBean;
import com.themetacity.typebeans.TagBean;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * This is the custom tag that formats an ArticleBean into an RSS <item>.
 * It is called in JSP pages.
 */
public class Item extends SimpleTagSupport {

    private ArticleBean article = new ArticleBean();

    private static final Logger logger = Logger.getLogger(Item.class);

    public void doTag() {
        JspWriter out = getJspContext().getOut();
        try {
            out.println("    <item>");
            out.println("        <title>" + article.getTitle() + "</title>");
            out.println("        <link>/" + article.getURL() + "</link>");
            out.println("        <description>" + article.getArticleText() + "</description>");
            out.println("        <guid>" + article.getURL() + "</guid>");
            out.println("        <pubdate>" + article.getDateTime() + "</pubdate>");
            out.println("    </item>");
        } catch (IOException IOEx) {
            logger.warn("There was an error with the article rendering");
            logger.warn(IOEx);
        }
    }

    public void release() {
        article = null;
    }

    public ArticleBean getArticle() {
        return article;
    }

    public void setArticle(ArticleBean article) {
        this.article = article;
    }
}