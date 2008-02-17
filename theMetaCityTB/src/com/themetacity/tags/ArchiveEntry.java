package com.themetacity.tags;

import com.themetacity.typebeans.ArchiveEntryBean;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class ArchiveEntry extends SimpleTagSupport {

    // Variables
    private ArchiveEntryBean archiveEntry = new ArchiveEntryBean();

    // Start processing
    public void doTag() {
        // * Initialise the context here as will not be valid in the container previously. *
        // The writer gives access to the page context so its possible to write output
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();

        try {
            out.println("<div class=\"archiveEntry\"><div class=\"archiveEntryTime\">" + buildDateLink(archiveEntry.getDateTime()) + "</div><div class=\"archiveEntryTitle\">" + buildTitleLink(archiveEntry.getTitle()) + "</div></div>");
        } catch (IOException IOEx) {
            System.out.print("There was an error with the article rendering");
            System.out.print(IOEx);
        }
    }

    // Free the Article used
    public void release() {
        archiveEntry = null;
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
     * Builds the date link for each article. It shows the year month and day the article was published and then links the respective date to show all the article publised on that date
     * e.g. 2007 (links to articles in 2007) / 12 (links to articles in 2007/12) / 1 (links to articles on the 2007/12/1)
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
        outputString.append("<a href=\"/").append(dateSplit[0]).append("/").append(dateSplit[1]).append("/").append(dateSplit[2]).append("\" />").append(dateSplit[2]).append("</a>");


        return outputString.toString();
    }

    public ArchiveEntryBean getArchiveEntry() {
        return archiveEntry;
    }

    public void setArchiveEntry(ArchiveEntryBean archiveEntry) {
        this.archiveEntry = archiveEntry;
    }
}
