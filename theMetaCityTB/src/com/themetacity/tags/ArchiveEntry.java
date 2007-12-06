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
            out.println("<div class=\"archiveEntry\">" + buildTtitle(archiveEntry.getTitle()) + " <span>" + buildDateLink(archiveEntry.getDateTime()) + "</span></div>");
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
     * Build the article title A and associated href
     * @param title is the unformatted tatle, straight from the database
     * @return an A string in href
     */
    public String buildTtitle(String title){
        return "<a href=\"/" + buildTitleLink(title) + "\">" + title + "</a>";
    }

    /**
     * This function will take a database encoded title and turn it into a hyphen/web encoded title
     *
     * @param titleToBuild is the string to encode with hypens
     * @return a hypen encoded string
     */
    public String buildTitleLink(String titleToBuild) {
        return titleToBuild.replace(" ", "-");
    }

    /**
     * @param dateToBuild is the date string to convert and do operations on.
     * @return a string with the dates seperated out
     */
    public String buildDateLink(String dateToBuild) {
        StringBuilder outpuString = new StringBuilder();

        String[] dateSplit = dateToBuild.split("-");
                         // |-- Start year                                                         end year --|                       |-- Start month                                                                                     end of month --|                       |-- Start day                                                                                                                             end day --|
        outpuString.append("<a href=\"").append(dateSplit[0]).append("\" />").append(dateSplit[0]).append("</a>").append("-").append("<a href=\"").append(dateSplit[0]).append("/").append(dateSplit[1]).append("\" />").append(dateSplit[1]).append("</a>").append("-").append("<a href=\"").append(dateSplit[0]).append("/").append(dateSplit[1]).append("/").append(dateSplit[2]).append("\" />").append(dateSplit[2]).append("</a>");
        return outpuString.toString();
    }

    public ArchiveEntryBean getArchiveEntry() {
        return archiveEntry;
    }

    public void setArchiveEntry(ArchiveEntryBean archiveEntry) {
        this.archiveEntry = archiveEntry;
    }
}
