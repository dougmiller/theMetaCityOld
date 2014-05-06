package com.themetacity.tags.blog;

import com.themetacity.typebeans.ArticleBean;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArchiveEntry extends SimpleTagSupport {

    // Variables
    private ArticleBean articleBean = new ArticleBean();
    private Date previousDate;

    private static final Logger logger = LogManager.getLogger(ArchiveEntry.class);

    /**
     * Take the archive entry and output a link to the article. This function also handles the
     * outputting dates when the years, months and days change.
     */
    public void doTag() {
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();

        Calendar articleCreatedDate = new GregorianCalendar();
        articleCreatedDate.setTime(articleBean.getCreatedDate());

        Calendar oldDate = new GregorianCalendar();

        // The first time this is called from the loop in the jsp page the oldDate is null. (there is no previous date)
        System.out.println(previousDate);
        if (previousDate != null){
            oldDate.setTime(previousDate);
        } // If it is null, this is the first entry and that is handled below with the ||

        try {
            if ((articleCreatedDate.get(Calendar.YEAR) != oldDate.get(Calendar.YEAR)) || previousDate == null) {
                out.println("<h2>" + articleCreatedDate.get(Calendar.YEAR) + "</h2>");
            }

            if ((articleCreatedDate.get(Calendar.MONTH) != oldDate.get(Calendar.MONTH)) || previousDate == null) {
                out.println("<h3>" + (articleCreatedDate.get(Calendar.MONTH) + 1) + "</h3>");   // months are indexed at 0
            }
            // Don't bother with day granularity
        }
        catch (IOException IOEx) {
            logger.warn("There was an error with the archive rendering");
            logger.warn(IOEx);
        }

        // now  the actual links to articles
        try {
            out.println("<h4 class=\"archiveEntryTitle\">" + buildTitleLink(articleBean.getTitle()) + "</h4>");
        } catch (IOException IOEx) {
            logger.warn("There was an error with the archive link rendering");
            logger.warn(IOEx);
        }
    }

    /**
     * Build the article title anchor and associated href
     *
     * @param title is the unformatted title, straight from the database
     * @return an anchor string with the href pointing to the article
     */
    public String buildTitleLink(String title) {
        return "<a href=\"/blog/" + articleBean.getURL() + "\">" + title + "</a>";
    }

    public ArticleBean getArticleBean() {
        return articleBean;
    }

    public void setArticleBean(ArticleBean articleBean) {
        this.articleBean = articleBean;
    }

    public Date getPreviousDate() {
        return previousDate;
    }

    public void setPreviousDate(Date previousDate) {
        this.previousDate = previousDate;
    }
}
