package com.themetacity.admintags;

import com.themetacity.typebeans.ArticleBean;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;

import java.io.IOException;

public class Articles extends SimpleTagSupport {

    private ArticleBean articleBean = new ArticleBean();
    
    private static Logger logger = Logger.getLogger(Articles.class);

    public void doTag() {
        // * Initialise the context here as will not be valid in the container previously. *
        // The writer gives access to the page context so its possible to write output
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();

        try {
            out.println("<div>");
            out.println("   <a href=\"/" + articleBean.buildTitle(articleBean.getTitle()) + "\">" + articleBean.getTitle() + "</a>");
            out.println("   " + articleBean.getDateTime());
            out.println("   <input type=\"hidden\" name=\"articleID\" value=\"" + articleBean.getArticleID() + "\"/>");
            out.println("   <button type=\"submit\" class=\"submitbutton\" name=\"submit\" value=\"edit\"><img src=\"siteimages/page_edit.png\" alt=\"Update\"/>Edit</button>");
            out.println("   <button type=\"submit\" class=\"deletebutton\" name=\"submit\" value=\"delete\"><img src=\"siteimages/delete.png\" alt=\"Delete\"/>Delete</button>");
            out.println("</div>");
        } catch (IOException IOEx) {
            logger.warn("There was an error with the article rendering");
            logger.warn(IOEx);
        }
    }

    // Free the Article used
    public void release() {
        articleBean = null;
    }

    public ArticleBean getArticleBean() {
        return articleBean;
    }

    public void setArticleBean(ArticleBean articleBean) {
        this.articleBean = articleBean;
    }
}
