package com.themetacity.tags.blog;

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

    private ArticleBean article = new ArticleBean();

    private static final Logger logger = Logger.getLogger(Article.class);

    // Start processing
    public void doTag() {
        // * Initialise the context here as will not be valid in the container previously. *
        // The writer gives access to the page context so its possible to write output
        JspWriter out = getJspContext().getOut();
        try {
            out.println("<article class=\"newsarticle\">");
            out.println("   <h1><a href=\"/blog/" + article.getURL() + "\">" + article.getTitle() + "</a></h1>");
            out.println("    " + article.getArticleText());
            out.println("    <footer class=\"tagmetainfo\">");
            out.println("        <p>"+ article.getTitle()  + ";");
            out.println("        Posted on: <time datetime=\"" + article.getCreatedDate() +"\">" + article.getCreatedDate() + "</time>;");
            out.println("        Posted under: " + formatTags(article.getTags()));
            out.println("        </p>");
            out.println("    </footer>");
            out.println("</article>");

        } catch (IOException IOEx) {
            logger.warn("There was an error with the article rendering");
            logger.warn(IOEx);
        }
    }

    public void release() {
        article = null;
    }

    private String formatTags(LinkedList<TagBean> tagList) {
        StringBuilder outputString = new StringBuilder();

        for (TagBean tag : tagList) {
            outputString.append("<a href=\"/blog/tags/").append(tag.getTag()).append("\">");
            outputString.append(tag.getTag());
            outputString.append("</a> ");
        }

        // There are not tags for this article
        if (outputString.length() == 0) {
            outputString.append("This article has no <a href=\"/blog/tags/\">tags</a> associated with it");
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
