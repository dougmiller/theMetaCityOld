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
public class Article extends SimpleTagSupport {

    // Variables
    private ArticleBean article = new ArticleBean();

    private static final Logger logger = Logger.getLogger(Article.class);

    // Start processing
    public void doTag() {
        // * Initialise the context here as will not be valid in the container previously. *
        // The writer gives access to the page context so its possible to write output
        JspWriter out = getJspContext().getOut();

        try {
            out.println("    <div class=\"newsarticle\">");
            out.println("        <h2><a href=\"/" + article.buildTitle(article.getTitle()) + "\">" + article.getTitle() + "</a></h2>");
            out.println("        " + article.getArticleText());
            out.println("        <p>Posted on: " + article.getDateTime() + "</p>");
            out.println("        <p class=\"tagsspan\">Posted under: " + formatTags(article.getTags()) + "</p>");
            out.println("    </div>");

        } catch (IOException IOEx) {
            logger.warn("There was an error with the article rendering");
            logger.warn(IOEx);
        }
    }

    // Free the Article used
    public void release() {
        article = null;
    }

    private String formatTags(LinkedList<TagBean> tagList) {
        StringBuilder outputString = new StringBuilder();

        for (TagBean tag : tagList) {
            outputString.append("<a href=\"/tags/").append(tag.getTag()).append("\">");
            outputString.append(tag.getTag());
            outputString.append("</a> ");
        }

        // There are not tags for this article
        if (outputString.length() == 0) {
            outputString.append("This article has no tags associated with it");
        }

        return outputString.toString().trim();
    }

    public ArticleBean getArticle() {
        return article;
    }

    public void setArticle(ArticleBean article) {
        this.article = article;
    }
}
