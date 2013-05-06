package com.themetacity.tags.sitemap;

import com.themetacity.typebeans.TagBean;
import com.themetacity.tags.blog.Article;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is the custom tag that formats a ArticleBean tags in to sitemap entries. It is called in JSP pages.
 */
public class ArticleTags extends SimpleTagSupport {

    private TagBean tag = new TagBean();

    private static final Logger logger = LogManager.getLogger(Article.class);

    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");

    public void doTag() {

        JspWriter out = getJspContext().getOut();

        try {
            out.println("    <url>");
            out.println("        <loc>http://www.themetacity.com/blog/tags/" + tag.getTag() + "</loc>");
            out.println("        <lastmod>" + formatter.format(tag.getLastUpdatedDate()) + "</lastmod>");
            out.println("        <changefreq>monthly</changefreq>");
            out.println("        <priority>0.4</priority>");
            out.println("    </url>");

        } catch (IOException IOEx) {
            logger.warn("There was an error with the tag rendering");
            logger.warn(IOEx);
        }
    }

    public void release() {
        tag = null;
    }

    public TagBean getTag() {
        return tag;
    }

    public void setTag(TagBean tag) {
        this.tag = tag;
    }
}
