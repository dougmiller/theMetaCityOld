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
            out.println("<div class=\"\">");
            out.println("<a href=\"" + buildTitleLink(archiveEntry.getTitle()) + "\" />" + archiveEntry.getTitle() + "</a>");
            out.println("</div>");
        } catch (IOException IObEx) {
            System.out.print("There was an error with the article rendering");
            System.out.print(IObEx);
        }
    }

    // Free the Article used
    public void release() {
        archiveEntry = null;
    }

    public String buildTitleLink(String toBuild){
        return toBuild.replace(" ", "-");
    }

    public ArchiveEntryBean getArchiveEntry() {
        return archiveEntry;
    }

    public void setArchiveEntry(ArchiveEntryBean archiveEntry) {
        this.archiveEntry = archiveEntry;
    }
}
