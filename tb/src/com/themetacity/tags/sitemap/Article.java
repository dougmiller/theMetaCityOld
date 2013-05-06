package com.themetacity.tags.sitemap;

import com.themetacity.typebeans.ArticleBean;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is the custom tag that formats a ArticleBean into sitemap entries.
 */
public class Article extends SimpleTagSupport {

    private ArticleBean article = new ArticleBean();

    private static final Logger logger = LogManager.getLogger(com.themetacity.tags.blog.Article.class);

    SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");

    public void doTag() {
        JspWriter out = getJspContext().getOut();
        try {
            out.println("    <url>");
            out.println("        <loc>http://www.themetacity.com/blog/" + article.getURL() + "</loc>");
            out.println("        <lastmod>" + formatter.format(article.getModifiedDate()) + "</lastmod>");
            out.println("        <changefreq>monthly</changefreq>");
            out.println("        <priority>0.8</priority>");
            out.println("    </url>");

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
