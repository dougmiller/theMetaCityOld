package com.themetacity.tags.blog;

import com.themetacity.typebeans.ArticleBean;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;

import java.io.IOException;

public class AdminArticle extends SimpleTagSupport {

    private ArticleBean articleBean = new ArticleBean();
    
    private static Logger logger = Logger.getLogger(AdminArticle.class);

    public void doTag() {
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();

        try {
            out.println("<div>");
            out.println("   <div><a href=\"/" + articleBean.buildURL(articleBean.getTitle()) + "\">" + articleBean.getTitle() + "</a>");
            out.println("   " + articleBean.getDateTime() + "</div>");
            out.println("   <input type=\"hidden\" name=\"articleID\" value=\"" + articleBean.getArticleID() + "\"/>");
            out.println("   <div><button type=\"submit\" class=\"submitbutton\" name=\"submit\" value=\"edit\"><img src=\"siteimages/page_edit.png\" alt=\"Update\"/>Edit</button></div>");
            out.println("   <div><button type=\"submit\" class=\"deletebutton\" name=\"submit\" value=\"delete\"><img src=\"siteimages/delete.png\" alt=\"Delete\"/>Delete</button></div>");
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
