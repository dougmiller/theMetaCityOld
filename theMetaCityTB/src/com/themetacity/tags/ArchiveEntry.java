package com.themetacity.tags;

import com.themetacity.typebeans.ArticleBean;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

import org.apache.log4j.Logger;

public class ArchiveEntry extends SimpleTagSupport {

    // Variables
    private ArticleBean articleBean = new ArticleBean();
    private String previousDate;

    private static final Logger logger = Logger.getLogger(ArchiveEntry.class);

    /**
     * Take the archive entry and output a link to the article. This function also handles the
     * outputting dates when the years, months and days change.
     */
    public void doTag() {
        // * Initialise the context here as will not be valid in the container previously. *
        // The writer gives access to the page context so its possible to write output
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();

        String[] splitPreviousDate = getYearMonthOfDateString(previousDate);
        String[] splitThisDate = getYearMonthOfDateString(articleBean.getDateTime());

        try {
            if (!splitPreviousDate[0].equals(splitThisDate[0])) {    //[0] is year
                out.println("<h2>" + splitThisDate[0] + "</h2>");
            }

            if ((!(splitPreviousDate[0]+splitPreviousDate[1]).equals(splitThisDate[0]+splitThisDate[1]))) {    //[1] is month
                out.println("<h3>" + splitThisDate[1] + "</h3>");
            }


        }
        catch (IOException IOEx) {
            logger.warn("There was an error with the archive rendering");
            logger.warn(IOEx);
        }


        try {
            out.println("<p class=\"archiveEntryTitle\">" + buildTitleLink(articleBean.getTitle()) + "</p>");
        } catch (IOException IOEx) {
            logger.warn("There was an error with the article rendering");
            logger.warn(IOEx);
        }
    }

    /**
     * Build the article title anchor and associated href
     *
     * @param title is the unformatted tatle, straight from the database
     * @return an A string in href
     */
    public String buildTitleLink(String title) {
        return "<a href=\"/" + articleBean.getURL() + "\">" + title + "</a>";
    }

    /**
     * Builds the date link for each article. It shows the year month and day the article was published and then Links the respective date to show all the article publised on that date
     * e.g. 2007 (Links to articles in 2007) / 12 (Links to articles in 2007/12) / 1 (Links to articles on the 2007/12/1)
     *
     * @param dateToBuild is the date string to convert and do operations on.
     * @return a anchor string with the dates seperated out and linked
     */
    public String buildDateLink(String dateToBuild) {
        StringBuilder outputString = new StringBuilder();

        String[] dateSplit = dateToBuild.split("-");

        // Year
        outputString.append("<a href=\"/").append(dateSplit[0]).append("\" />").append(dateSplit[0]).append("</a>");
        outputString.append("-");
        // Month
        outputString.append("<a href=\"/").append(dateSplit[0]).append("/").append(dateSplit[1]).append("\" />").append(dateSplit[1]).append("</a>");
        outputString.append("-");
        // Day
        String[] dateCleanForEnd = dateSplit[2].split(" ");
        outputString.append("<a href=\"/").append(dateSplit[0]).append("/").append(dateSplit[1]).append("/").append(dateCleanForEnd[0]).append("\" />").append(dateCleanForEnd[0]).append("</a>");


        return outputString.toString();
    }

    private String[] getYearMonthOfDateString(String dateToParse) {
        if (dateToParse.equals("")) {
            return new String[]{"", ""};
        }
        String[] dateSplitwithTime = dateToParse.split("-");
        return new String[]{dateSplitwithTime[0], dateSplitwithTime[1]};
    }

    public ArticleBean getArticleBean() {
        return articleBean;
    }

    public void setArticleBean(ArticleBean articleBean) {
        this.articleBean = articleBean;
    }

    public String getPreviousDate() {
        return previousDate;
    }

    public void setPreviousDate(String previousDate) {
        this.previousDate = previousDate;
    }
}
