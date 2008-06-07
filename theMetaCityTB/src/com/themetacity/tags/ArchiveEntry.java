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

    private static final Logger logger = Logger.getLogger(ArchiveEntry.class);

    // Start processing
    public void doTag() {
        // * Initialise the context here as will not be valid in the container previously. *
        // The writer gives access to the page context so its possible to write output
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();

        try {
            out.println("<div class=\"articleBean\"><div class=\"archiveEntryTime\">" + buildDateLink(articleBean.getDateTime()) + "</div><div class=\"archiveEntryTitle\">" + buildTitleLink(articleBean.getTitle()) + "</div></div>");
        } catch (IOException IOEx) {
            logger.warn("There was an error with the article rendering");
            logger.warn(IOEx);
        }
    }

    /**
     * Build the article title anchor and associated href
     * @param title is the unformatted tatle, straight from the database
     * @return an A string in href
     */
    public String buildTitleLink(String title){
        return "<a href=\"/" + convertTitleToWeb(title) + "\">" + title + "</a>";
    }

    /**
     * This function will take a database encoded title and turn it into a hyphen/web encoded title
     *
     * @param titleToBuild is the string to encode with hypens
     * @return a hypen encoded string
     */
    public String convertTitleToWeb(String titleToBuild) {
        return titleToBuild.replace(" ", "-");
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

    public ArticleBean getArticleBean() {
        return articleBean;
    }

    public void setArticleBean(ArticleBean articleBean) {
        this.articleBean = articleBean;
    }
}
